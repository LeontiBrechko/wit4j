/*
 * Copyright 2016 Liavontsi Brechka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.liavontsibrechka.wit4j.communication;

import com.fasterxml.jackson.databind.JsonNode;
import com.liavontsibrechka.wit4j.Context;
import com.liavontsibrechka.wit4j.Wit;
import com.liavontsibrechka.wit4j.action.Actions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Class for communication with <b>Wit.ai</b> bot through <em>converse</em> request type of <b>Wit.ai HTTP API</b>.
 *
 * @author Liavontsi Brechka
 * @see HttpCommunication
 * @see HttpCommunicationType
 * @since 1.0-SNAPSHOT
 */
public class Converse extends HttpCommunication {
    /**
     * Unique session identifier to group messages from the same user request/conversation.
     */
    private String sessionId;
    /**
     * Reference to instance of {@code Context} singleton class that represents current context of application
     * (the object that represents the session state).
     *
     * @see Context
     */
    private Context context;
    /**
     * Reference to instance of {@code Actions} singleton class that contains link to actions map object.
     * <p>
     * See {@code Actions} class description for more information about actions map.
     *
     * @see Wit
     * @see Actions
     */
    private Actions actions;

    /**
     * Creates instance of {@code Converse} class and instantiate variables described above.
     */
    protected Converse() {
        super();

        sessionId = UUID.randomUUID().toString();
        context = Context.getInstance();
        actions = Actions.getInstance();
    }

    /**
     * Creates query string, final URL for request and proper http connection to make a request to
     * <b>Wit.ai HTTP API</b>. As a result, provides resources for reading JSON response.
     *
     * @param message message provided by user input (May be null).
     * @return JSON response object that is represented as {@code JsonNode}.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public JsonNode sendHttpRequest(String message) throws IOException {
        String queryString = baseUrl + "/converse?v=" + version + "&session_id=" + sessionId;
        if (message != null) {
            queryString += ("&q=" + URLEncoder.encode(message, "UTF-8"));
        }

        finalUrl = new URL(queryString);
        httpConnection = (HttpURLConnection) finalUrl.openConnection();
        httpConnection.setRequestProperty("Content-Type", "application/json");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setRequestProperty("Authorization", "Bearer " + Wit.getToken());
        httpConnection.setDoOutput(true);

        jsonMapper.writeValue(httpConnection.getOutputStream(), context.getContext());
        return jsonMapper.readTree(httpConnection.getInputStream());
    }

    /**
     * Handles JSON response according to type of bot response (<em>merge</em> - first bot action after a user message,
     * <em>msg</em> - the bot has something to say, <em>action</em> - the bot has something to do
     * or <em>stop</em> - the bot is waiting to proceed).
     *
     * @param httpResponse {@code JsonNode} object that represents JSON response of {@code sendHttpRequest} call.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public void handleHttpResponse(JsonNode httpResponse) throws IOException {
        switch (ConverseResponseType.valueOf(httpResponse.get("type").asText().toUpperCase())) {
            case MERGE:
                actions.getAction("merge")
                        .execute(sessionId, Context.getInstance(), httpResponse);
                //follow-up request
                handleHttpResponse(sendHttpRequest(null));
                break;
            case MSG:
                actions.getAction("say")
                        .execute(sessionId, Context.getInstance(), httpResponse);
                //follow-up request
                handleHttpResponse(sendHttpRequest(null));
                break;
            case ACTION:
                actions.getAction(httpResponse.get("action").asText())
                        .execute(sessionId, Context.getInstance(), httpResponse);
                //follow-up request
                handleHttpResponse(sendHttpRequest(null));
                break;
            case STOP:
                sessionId = UUID.randomUUID().toString();
                break;
            default:
                // TODO: 2016-06-25 error response
                break;
        }
    }
}
