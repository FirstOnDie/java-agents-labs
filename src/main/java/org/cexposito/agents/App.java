package org.cexposito.agents;

import dev.langchain4j.chain.ConversationalChain;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    interface Assistant {
        @SystemMessage("""
            Eres un asistente conciso. 
            Si el usuario te dice su nombre, recuérdalo y respóndelo cuando te pregunte.
            """)
        String chat(@UserMessage String userMessage);
    }

    public static void main(String[] args) throws Exception {
        var memory = MessageWindowChatMemory.withMaxMessages(8);

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatModel(buildModel())
                .chatMemory(memory)
                .build();

        System.out.println("""
                === Chat con memoria (AiServices, ventana=8) ===
                Comandos: :exit | :clear
                """);

        try (var br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.print("Tú > ");
                String user = br.readLine();
                if (user == null) break;

                if (user.equalsIgnoreCase(":exit")) {
                    System.out.println("Saliendo...");
                    break;
                }
                if (user.equalsIgnoreCase(":clear")) {
                    memory.clear();
                    System.out.println("[Memoria] Conversación borrada.");
                    continue;
                }

                System.out.println("Bot > " + assistant.chat(user));
            }
        }
    }

    private static OpenAiChatModel buildModel() {

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

        return model;
    }
}
