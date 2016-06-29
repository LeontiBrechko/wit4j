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

package com.liavontsibrechka.wit4j;

import com.liavontsibrechka.wit4j.action.Actions;
import com.liavontsibrechka.wit4j.action.IAction;
import com.liavontsibrechka.wit4j.communication.HttpCommunicationType;
import com.liavontsibrechka.wit4j.interaction.InteractionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * The {@code Wit} class should be considered as a starting point of using <b>wit4j</b>
 * library in Java applications. It provides user with a comfortable way to establish connection
 * and communicate continuously with <em>Wit.ai</em> bot. The ways of interaction with user and bot communication
 * may be easily added by extending {@code Interaction} and {@code HttpCommunication} classes.
 * <p>
 * This class has two instance methods for establishing console interaction ({@code interactByConsole} method)
 * and custom interaction ({@code interact} method).
 *
 * @author Liavontsi Brechka
 * @see com.liavontsibrechka.wit4j.interaction.Interaction
 * @see com.liavontsibrechka.wit4j.communication.HttpCommunication
 * @since 1.0-SNAPSHOT
 */
// TODO: 2016-06-26 consider singleton implementation
public class Wit {
    /**
     * Access token value in order to establish <em>OAuth2</em> connection with <b>Wit.ai HTTP API</b>
     */
    private static String token;

    /**
     * Creates an instance of {@code Wit} class ans saves its {@code token} for later use.
     * {@code actions} argument is stored in {@code Actions} singleton class that represents available actions
     * for a particular application.
     *
     * @param token   access token for establishing <em>OAuth2</em> connection
     * @param actions map ({@code Map<String, IAction>}) that contains action name
     *                as a {@code String} key and action implementation as an {@code IAction}
     *                instance in order to handle appropriate actions triggered by bot.
     * @throws IllegalAccessException is thrown if attempt was made to assign a new object to
     *                                {@code Map<String, IAction> actions} reference of {@code Actions}
     *                                singleton class more than one time.
     * @see IAction
     * @see Actions
     */
    public Wit(String token, Map<String, IAction> actions) throws IllegalAccessException {
        Wit.token = token;
        Actions.setActions(actions);
    }

    /**
     * Token getter method.
     *
     * @return the application's token.
     */
    public static String getToken() {
        return token;
    }

    /**
     * Starts interaction with a user through standard console and its streams
     * ({@code System.in}, {@code System.out}) by creating an instance of {@code Interaction} subclasses using
     * {@code InteractionFactory} class.
     *
     * @param httpCommunicationType enum type of {@code HttpCommunicationType} for dynamic instantiation of
     *                              {@code HttpCommunication} subclasses through {@code HttpCommunicationFactory}.
     * @throws IOException if an I/O error occurs.
     * @see HttpCommunicationType
     * @see com.liavontsibrechka.wit4j.communication.HttpCommunication
     * @see com.liavontsibrechka.wit4j.communication.HttpCommunicationFactory
     * @see InteractionFactory
     */
    public void interactByConsole(HttpCommunicationType httpCommunicationType) throws IOException {
        InteractionFactory.getConsoleInteraction(httpCommunicationType).interact();
    }

    /**
     * Starts interaction with user based on streams provided by creating an instance of {@code Interaction}
     * subclasses using {@code InteractionFactory} class.
     *
     * @param input                 stream to get data from user.
     * @param output                stream to provide user with data.
     * @param httpCommunicationType enum type of {@code HttpCommunicationType} for dynamic instantiation of
     *                              {@code HttpCommunication} subclasses through {@code HttpCommunicationFactory}.
     * @throws IOException if an I/O error occurs.
     * @see HttpCommunicationType
     * @see com.liavontsibrechka.wit4j.communication.HttpCommunication
     * @see com.liavontsibrechka.wit4j.communication.HttpCommunicationFactory
     * @see InteractionFactory
     */
    public void interact(InputStream input, OutputStream output, HttpCommunicationType httpCommunicationType)
            throws IOException {
        InteractionFactory.getCustomInteraction(input, output, httpCommunicationType).interact();
    }
}
