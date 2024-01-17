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
public class ClientForTicketRequestDTO {
        @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of client")
        private Long id;
}
