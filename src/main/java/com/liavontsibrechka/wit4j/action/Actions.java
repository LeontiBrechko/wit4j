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

package com.liavontsibrechka.wit4j.action;

import java.util.Map;

/**
 * Singleton class that contains reference to actions map object.
 * <p>
 * Actions map ({@code Map<String, IAction>}) - is the map that contains action name
 * as a {@code String} key and action implementation as an {@code IAction}
 * instance in order to handle appropriate actions triggered by bot.
 *
 * @author Liavontsi Brechka
 * @since 1.0-SNAPSHOT
 */
public class Actions {
    private static Actions instance;
    private volatile static boolean instanceCreated = false;

    /**
     * Actions map of specific application.
     */
    private final Map<String, IAction> actions;

    private Actions(Map<String, IAction> actions) {
        this.actions = actions;
    }

    // TODO: 2016-06-25 review this approach of creating and setting actions
    public static Actions setActions(Map<String, IAction> actions) throws IllegalAccessException {
        boolean flag = false;

        if (!instanceCreated) {
            synchronized (Actions.class) {
                if (!instanceCreated) {
                    instance = new Actions(actions);
                    instanceCreated = true;
                    flag = true;
                }
            }
        }

        if (!flag) {
            throw new IllegalAccessException("Actions Map can be set only once.");
        }
        return instance;
    }

    public static Actions getInstance() {
        if (!instanceCreated) {
            synchronized (Actions.class) {
                if (!instanceCreated) {
                    throw new IllegalStateException("Actions Map must be set before getting an instance.");
                }
            }
        }
        return instance;
    }

    /**
     * Getter for specific action to execute.
     *
     * @param actionName name of the action to execute.
     * @return reference to the action.
     */
    public IAction getAction(String actionName) {
        if (!actions.containsKey(actionName)) {
            throw new IllegalArgumentException("IAction with name " + actionName + " was not fount in actions Map");
        }
        return actions.get(actionName);
    }
}
