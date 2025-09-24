package dev.eliezer.gestaohelpdesk.dto;

import dev.eliezer.gestaohelpdesk.modules.category.entities.Category;
import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {
    @Schema(example = "9",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of ticket")
    private Long id;
    @Schema(example = "Erro no fechamento do caixa",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "short description of ticket")
    private String shortDescription;
    @Schema(example = "Relatório de fechamento do caixa apresenta valores divergentes",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "description of ticket")
    private String description;
    private ClientForTicketResponseDTO client;
    private UserForTicketResponseDTO user;
    private Status status;
    private Long typeOfService;
    private Category category;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime slaDateTimeEnd;
}
