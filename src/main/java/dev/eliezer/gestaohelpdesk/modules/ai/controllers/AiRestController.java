package dev.eliezer.gestaohelpdesk.modules.ai.controllers;

import dev.eliezer.gestaohelpdesk.modules.ai.useCases.SummarizeTicketUseCase;
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

    private final SummarizeTicketUseCase summarizeTicketUseCase;

    public AiRestController(SummarizeTicketUseCase summarizeTicketUseCase) {
        this.summarizeTicketUseCase = summarizeTicketUseCase;
    }

    // Exemplo simples para testar um prompt no Gemini via Spring AI
    public String generation(@RequestParam("q") String userInput) {
        return summarizeTicketUseCase.execute(userInput);
    }
}
