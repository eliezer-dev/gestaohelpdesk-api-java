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
public class TicketCountResponseDTO {
    @Schema(example = "50",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "count of allTickets")
    private Long allTicketsCount;
    @Schema(example = "20",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "count all tickets assigned to user")
    private Long ticketsAssignedUserCount;
    @Schema(example = "30",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "count all tickets assigned to other users")
    private Long ticketsAssignedOtherUsersCount;

    @Schema(example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "count all tickets not assigned to any users")
    private Long ticketsNotAssignedCount;

    @Schema(example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "count all completed tickets")
    private Long completedTicketsCount;

    @Schema(example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "count all closed tickets")
    private Long closedTicketsCount;

}
