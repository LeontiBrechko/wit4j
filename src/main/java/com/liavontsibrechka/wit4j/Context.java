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

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Singleton class that contains reference to context ({@code ObjectNode}) object that represents the current session
 * state of an application.
 *
 * @author Liavontsi Brechka
 * @see ObjectNode
 * @since 1.0-SNAPSHOT
 */
public class Context {
    private static Context instance;
    private volatile static boolean instanceCreated = false;

    private ObjectNode context;

    private Context() {
        context = new ObjectNode(JsonNodeFactory.instance);
    }

    public static Context getInstance() {
        if (!instanceCreated) {
            synchronized (Context.class) {
                if (!instanceCreated) {
                    instance = new Context();
                    instanceCreated = true;
                }
            }
        }
        return instance;
    }

    /**
     * Updates {@code context} with specific entities.
     *
     * @param entities JSON object that contains entities to update in the {@code context} object.
     */
    public void updateContext(ObjectNode entities) {
        context.setAll(entities);
    }

    public ObjectNode getContext() {
        return context;
    }
}
