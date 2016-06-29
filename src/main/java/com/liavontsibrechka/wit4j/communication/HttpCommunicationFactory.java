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

/**
 * Factory class for creating instances of concrete {@code HttpCommunication} subclasses using
 * {@code HttpCommunicationType} enumeration.
 *
 * @author Liavontsi Brechka
 * @see HttpCommunication
 * @see HttpCommunicationType
 * @since 1.0-SNAPSHOT
 */
public class HttpCommunicationFactory {
    public static HttpCommunication createHttpCommunication(HttpCommunicationType communicationType) {
        return communicationType.getCommunication();
    }
}
