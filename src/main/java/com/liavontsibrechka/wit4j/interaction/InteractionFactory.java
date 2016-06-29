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

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Factory class for instantiating {@code Interaction} subclasses' objects.
 *
 * @author Liavontsi Brechka
 * @see Interaction
 * @since 1.0-SNAPSHOT
 */
public class InteractionFactory {
    /**
     * Creates an object of {@code ConsoleInteraction} class that lets user to interact with bot via standard console
     * ({@code System.in} and {@code System.out})
     *
     * @param httpCommunicationType enum type of {@code HttpCommunicationType} for dynamic instantiation of
     *                              {@code HttpCommunication} subclasses through {@code HttpCommunicationFactory}.
     * @return reference to new instance of concrete {@code ConsoleInteraction} class that is a subclass of
     * {@code Interaction}.
     * @see com.liavontsibrechka.wit4j.communication.HttpCommunication
     * @see HttpCommunicationType
     * @see ConsoleInteraction
     */
    public static Interaction getConsoleInteraction(HttpCommunicationType httpCommunicationType) {
        return new ConsoleInteraction(httpCommunicationType);
    }

    /**
     * Creates an object of {@code CustomInteraction} class that lets user to interact with bot via custom
     * {@code InputStream} and {@code OutputStream}.
     *
     * @param input                 stream for reading user input.
     * @param output                stream for writing information to user.
     * @param httpCommunicationType enum type of {@code HttpCommunicationType} for dynamic instantiation of
     *                              {@code HttpCommunication} subclasses through {@code HttpCommunicationFactory}.
     * @return reference to new instance of concrete {@code CustomInteraction} class that is a subclass of
     * {@code Interaction}.
     */
    public static Interaction getCustomInteraction(InputStream input, OutputStream output,
                                                   HttpCommunicationType httpCommunicationType) {
        return new CustomInteraction(input, output, httpCommunicationType);
    }
}
