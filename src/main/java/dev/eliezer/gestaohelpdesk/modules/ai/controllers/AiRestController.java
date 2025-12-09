package dev.eliezer.gestaohelpdesk.modules.ai.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@SecurityRequirement(name = "jwt_auth")
public class AiRestController {

    private final ChatModel chatModel;

    public AiRestController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    // Exemplo simples para testar um prompt no Gemini via Spring AI
    @GetMapping("/ai")
    public String generation(@RequestParam("q") String userInput) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        userInput,
                        GoogleGenAiChatOptions.builder()
                                .temperature(0.4)
                                .build()
                ));

        // Extrai o conteúdo textual da resposta
        return response.getResult().getOutput().getText();
    }
}
