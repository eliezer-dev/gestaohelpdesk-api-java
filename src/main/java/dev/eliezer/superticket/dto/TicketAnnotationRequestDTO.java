package dev.eliezer.superticket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketAnnotationRequestDTO {

    @Schema(example = "Fiz diversos testes relacionados ao problema descrito e não apresentou erro",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "annotation of ticket")
    private String description;

    @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "user id of annotation")
    private Long userId;

    @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "ticket id of annotation")
    private Long ticketId;

}
