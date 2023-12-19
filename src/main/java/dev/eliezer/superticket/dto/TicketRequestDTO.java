package dev.eliezer.superticket.dto;

import dev.eliezer.superticket.domain.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class TicketRequestDTO {
    @Schema(example = "9",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of ticket")
    private Long id;
    @Schema(example = "Erro no fechamento do caixa",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "short description of ticket")
    private String shortDescription;
    @Schema(example = "Relatório de fechamento do caixa apresenta valores divergentes",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "description of ticket")
    private String description;
    private ClientForTicketRequestDTO client;
    private List<UserForTicketRequestDTO> users;
    private Status status;
}
