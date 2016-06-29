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

package com.liavontsibrechka.wit4j.interaction;

import com.liavontsibrechka.wit4j.communication.HttpCommunication;
import com.liavontsibrechka.wit4j.communication.HttpCommunicationFactory;
import com.liavontsibrechka.wit4j.communication.HttpCommunicationType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class provides a skeleton for user interaction with a bot by using two streams
 * ({@code input} and {@code output}) for taking and giving data to user and
 * an object of {@code HttpCommunication} class for communicating with bot.
 *
 * @author Liavontsi Brechka
 * @see HttpCommunication
 * @since 1.0-SNAPSHOT
 */
public abstract class Interaction {
    /**
     * Stream for reading user input.
     */
    protected InputStream input;
    /**
     * Stream for writing information to user.
     */
    protected OutputStream output;
    /**
     * {@code HttpCommunication} object for sending requests and retrieving responses from bot through
     * <b>Wit.ai HTTP API</b>.
     */
    protected HttpCommunication httpCommunication;

    /**
     * Creates interaction object that make possible for user to communicate with bot.
     *
     * @param input                 stream for reading user input.
     * @param output                stream for writing information to user.
     * @param httpCommunicationType enum type of {@code HttpCommunicationType} for dynamic instantiation of
     *                              {@code HttpCommunication} subclasses through {@code HttpCommunicationFactory}.
     * @see HttpCommunicationType
     * @see HttpCommunicationFactory
     */
    protected Interaction(InputStream input, OutputStream output, HttpCommunicationType httpCommunicationType) {
        this.input = input;
        this.output = output;
        this.httpCommunication = HttpCommunicationFactory.createHttpCommunication(httpCommunicationType);
    }

    /**
     * Describes the way of user interaction with bot.
     *
     * @throws IOException if an I/O error occurs.
     */
    public abstract void interact() throws IOException;
}