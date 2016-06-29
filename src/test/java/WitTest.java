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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.liavontsibrechka.wit4j.Wit;
import com.liavontsibrechka.wit4j.action.IAction;
import com.liavontsibrechka.wit4j.communication.HttpCommunicationType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Test class for <em>weatherTest</em> application.
 * <a href="https://wit.ai/LeontiBrechko/weatherTest">Link to Wit.ai application page.</a>
 *
 * @author Liavontsi Brechka
 * @since 1.0-SNAPSHOT
 */
public class WitTest {
    private static Map<String, IAction> actions = new HashMap<>();

    static {
        actions.put("merge", (sessionId, context, httpResponse) -> {
            JsonNode entitiesObj = httpResponse.get("entities");
            if (entitiesObj == null) {
                throw new IllegalArgumentException("No 'entities' object was found in json responce file.");
            }

            ObjectNode entities = new ObjectNode(JsonNodeFactory.instance);

            Iterator<String> fieldNames = entitiesObj.fieldNames();
            String nextField;
            while (fieldNames.hasNext()) {
                nextField = fieldNames.next();
                entities.set(nextField, findFirstEntityValue(entitiesObj, nextField));
            }

            context.updateContext(entities);
        });

        actions.put("say", (sessionId, context, httpResponse) ->
                System.out.println("Bot: " + httpResponse.get("msg").asText()));

        actions.put("fetchForecast", (sessionId, context, httpResponse) ->
                context.getContext().put("forecastResult", "sunny"));
    }

    public static JsonNode findFirstEntityValue(JsonNode entities, String entityName) {
        boolean flag = !entities.isNull() && !entities.get(entityName).isNull()
                && entities.get(entityName).isArray() && entities.get(entityName).size() > 0
                && !entities.get(entityName).get(0).isNull();

        if (!flag) {
            throw new IllegalArgumentException("Invalid entity name or entities object");
        }

        JsonNode entityValues = entities.get(entityName).get(0);
        if (entityValues.get("value").isObject()) {
            return entityValues.get("value").get("value");
        } else {
            return entityValues.get("value");
        }
    }

    public static void main(String[] args) throws IOException, IllegalAccessException {
        Wit wit = new Wit("L5DOSNSZQ3CTZ2DTQFVTXLRTQRO6TL4H", actions);
        wit.interactByConsole(HttpCommunicationType.CONVERSE);
    }
}
