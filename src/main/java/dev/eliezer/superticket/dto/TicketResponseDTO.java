package dev.eliezer.superticket.dto;

import dev.eliezer.superticket.domain.model.Category;
import dev.eliezer.superticket.domain.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<UserForTicketResponseDTO> users;
    private Status status;
    private Long typeOfService;
    private Category category;
    private LocalDateTime scheduledDateTime;
    private LocalDateTime createAt;
}
