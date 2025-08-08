package org.cexposito.agents;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class App {
    public static void main(String[] args) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1") // Use the demo endpoint for testing
                .apiKey("demo") // Use "demo" for testing purposes; replace with your actual API key
                .modelName("gpt-4o-mini")
                .build();

        String answer = model.chat("Di 'Hola LangChain4j' exactamente así.");
        System.out.println(answer);
    }
}
