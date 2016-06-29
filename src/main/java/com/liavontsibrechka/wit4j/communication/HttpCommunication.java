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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liavontsibrechka.wit4j.util.ResourceBundleEnum;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is responsible for defining an abstract structure of making http requests and handling http responses
 * (json files) using <b>Wit.ai HTTP API</b> in order to communicate with bot.
 *
 * @author Liavontsi Brechka
 * @since 1.0-SNAPSHOT
 */
public abstract class HttpCommunication {
    /**
     * Base url of <b>Wit.ai HTTP API</b>.
     */
    protected static String baseUrl;
    /**
     * <b>Wit.ai HTTP API</b> versioning parameter.
     */
    protected static String version;
    /**
     * Object for handling JSON files in java using <b>Jackson API</b>.
     *
     * @see ObjectMapper
     */
    protected static ObjectMapper jsonMapper;

    /**
     * Instantiating static fields described above using {@code ResourceBundle}.
     *
     * @see ResourceBundleEnum
     */
    static {
        ResourceBundle bundle = ResourceBundle.getBundle(ResourceBundleEnum.MAIN_APP_PROPERTIES.getFileName());
        baseUrl = bundle.getString("base_url");
        version = bundle.getString("version");
        jsonMapper = new ObjectMapper();
    }

    /**
     * URL for actual request ({@code baseUrl} + all necessary parameters)
     *
     * @see URL
     */
    protected URL finalUrl;
    /**
     * Http connection that is responsible for setting request headers and body as well as reading response.
     *
     * @see HttpURLConnection
     */
    protected HttpURLConnection httpConnection;

    /**
     * Prepares and sends http request to bot API.
     *
     * @param message message provided by user input (May be null).
     * @return JSON response from <b>Wit.ai HTTP API</b>.
     * @throws IOException if an I/O error occurs.
     * @see JsonNode
     */
    public abstract JsonNode sendHttpRequest(String message)
            throws IOException;

    /**
     * Handles JSON response that is returned by appropriate {@code sendHttpRequest} method call.
     *
     * @param httpResponse {@code JsonNode} object that represents JSON response of {@code sendHttpRequest} call.
     * @throws IOException if an I/O error occurs.
     * @see JsonNode
     */
    public abstract void handleHttpResponse(JsonNode httpResponse) throws IOException;
}
