package org.cexposito.agents;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class App {
    public static void main(String[] args) {
        boolean useDemo = Boolean.parseBoolean(System.getenv().getOrDefault("USE_DEMO", "true"));
        String openAiKey = System.getenv("OPENAI_API_KEY");

        OpenAiChatModel model;
        if (!useDemo && openAiKey != null && !openAiKey.isBlank()) {
            model = OpenAiChatModel.builder()
                    .apiKey(openAiKey)
                    .modelName("gpt-4o-mini")
                    .build();
        } else {
            model = OpenAiChatModel.builder()
                    .baseUrl("http://langchain4j.dev/demo/openai/v1") // Use the demo endpoint for testing
                    .apiKey("demo") // Use "demo" for testing purposes; replace with your actual API key
                    .modelName("gpt-4o-mini")
                    .build();
        }

        String answer = model.chat("Di 'Hola LangChain4j' exactamente así.");
        System.out.println(answer);
    }
}
