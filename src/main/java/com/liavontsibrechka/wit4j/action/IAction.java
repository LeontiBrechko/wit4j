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

import com.fasterxml.jackson.databind.JsonNode;
import com.liavontsibrechka.wit4j.Context;

/**
 * Functional interface that must be implemented by every action in {@code Map<String, IAction>} (actions map
 * for application).
 * <p>
 * See {@code Actions} class description for more information about actions map.
 *
 * @author Liavontsi Brechka
 * @see Actions
 * @since 1.0-SNAPSHOT
 */
public interface IAction {
    void execute(String sessionId, Context context, JsonNode httpResponse);
}
