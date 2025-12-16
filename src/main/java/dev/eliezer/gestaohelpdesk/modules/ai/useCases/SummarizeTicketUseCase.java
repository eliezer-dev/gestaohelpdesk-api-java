package dev.eliezer.gestaohelpdesk.modules.ai.useCases;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
public class SummarizeTicketUseCase {
    private final ChatModel chatModel;

    public SummarizeTicketUseCase(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String execute(String userInput) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        userInput,
                        GoogleGenAiChatOptions.builder()
                                .temperature(0.4)
                                .build()
                )
        );

        // Extrai o conteúdo textual da resposta
        return response.getResult().getOutput().getText();
    }
}
