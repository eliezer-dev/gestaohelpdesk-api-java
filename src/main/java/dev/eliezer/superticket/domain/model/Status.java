package dev.eliezer.superticket.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity(name = "tb_status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of status")
    Long id;

    @NotBlank(message = "description is not provided.")
    @Schema(example = "Em Andamento",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "status of ticket")
    String description;
    @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = """
            type of status:
            1:  standard.
            2: done.
            3: closed.
            4: pending.
            5: canceled.
            """)
    Long type;


}
