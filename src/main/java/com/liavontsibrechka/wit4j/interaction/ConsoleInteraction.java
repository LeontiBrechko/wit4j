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

import com.liavontsibrechka.wit4j.communication.HttpCommunicationType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Concrete class that provides {@code interact} method implementation of abstract {@code Interaction} class
 * in order to let user interact with bot via standard console streams ({@code System.in} and {@code System.out}).
 *
 * @author Liavontsi Brechka
 * @see Interaction
 * @since 1.0-SNAPSHOT
 */

// TODO: 2016-06-25 consider abstract factory pattern for interaction + communication
public class ConsoleInteraction extends Interaction {
    /**
     * Reader for user input.
     */
    protected BufferedReader reader;
    /**
     * Writer for printing output to user.
     */
    protected PrintWriter writer;

    /**
     * Message sent by user
     */
    protected String message;

    /**
     * Creates concrete implementation of {@code Interaction} class via standard console ({@code System.in}
     * and {@code System.out} are encapsulated by {@code BufferedReader} and {@code PrintWriter} respectively)
     * by using specified {@code HttpCommunicationType}.
     *
     * @param httpCommunicationType enum type of {@code HttpCommunicationType} for dynamic instantiation of
     *                              {@code HttpCommunication} subclasses through {@code HttpCommunicationFactory}.
     * @see com.liavontsibrechka.wit4j.communication.HttpCommunication
     * @see HttpCommunicationType
     * @see com.liavontsibrechka.wit4j.communication.HttpCommunicationFactory
     */
    protected ConsoleInteraction(HttpCommunicationType httpCommunicationType) {
        super(System.in, System.out, httpCommunicationType);

        reader = new BufferedReader(new InputStreamReader(this.input));
        writer = new PrintWriter(this.output);
    }

    /**
     * Reads input and writes appropriate output to user. Communicates with bot through specific
     * {@code HttpCommunication} object and its methods ({@code handleHttpResponse} and {@code sendHttpRequest}).
     *
     * @throws IOException IOException if an I/O error occurs.
     * @see com.liavontsibrechka.wit4j.communication.HttpCommunication
     */
    // TODO: 2016-06-26 review this method
    @Override
    public void interact() throws IOException {
        while (true) {
            writer.print("Me: ");
            writer.flush();
            while ("".equals((message = reader.readLine()).trim())) {
                writer.println();
                writer.print("Me: ");
                writer.flush();
            }

            httpCommunication.handleHttpResponse(
                    httpCommunication.sendHttpRequest(message)
            );
        }
    }
}
