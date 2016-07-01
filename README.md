# Wit4j
Java library to communicate with Wit.ai bot using Wit.ai HTTP API.

## Code example
```java
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
        Wit wit = new Wit("someTokenString", actions);
        wit.interactByConsole(HttpCommunicationType.CONVERSE);
    }
}
```

## Motivation
The reason for creating this library is to provide Java developers with base abstraction and some implementation of Wit.ai HTTP API in order to comfortably create bot applications in Java programming language.

## Installation
```xml
    <dependencies>
        <dependency>
            <groupId>com.liavontsibrechka</groupId>
            <artifactId>wit4j</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>wit4j-mvn-repo</id>
            <url>https://raw.github.com/leontibrechko/wit4j/mvn-repo/</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
```

Link for .jar file: http://www.liavontsibrechka.com/wit4j.jar

## API Reference
Java DOCs link: http://www.liavontsibrechka.com/apidocs/index.html

## License

Copyright 2016 Liavontsi Brechka

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
