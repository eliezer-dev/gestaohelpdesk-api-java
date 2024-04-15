package dev.eliezer.superticket.dto;

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
public class TicketAnnotationResponseDTO {
    @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of annotation")
    private Long id;

    @Schema(example = "Fiz diversos testes relacionados ao problema descrito e não apresentou erro",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "annotation of ticket")
    private String description;

    private UserForTicketResponseDTO user;

    @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "ticket id of annotation")
    private Long ticketId;

    @Schema(example = "2024-03-25T10:30:00", description ="date and time the ticket annotation was created")
    private LocalDateTime createAt;

    @Schema(example = "2024-03-25T10:30:00", description ="date and time the ticket annotation was updated")
    private LocalDateTime updateAt;

}
