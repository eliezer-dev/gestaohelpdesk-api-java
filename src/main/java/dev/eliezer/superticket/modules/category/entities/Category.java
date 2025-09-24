package dev.eliezer.superticket.modules.category.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "tb_category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "9",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of category")
    Long id;
    @Schema(example = "Problemas com emissão de documentos fiscais",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "description of category")
    @NotBlank(message = "[description] is not provided.")
    @Column(nullable = false)
    String description;

    @Schema(example = "0 - default priority" +
            "1 - urgency priority",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "priority of category")
    @NotNull(message = "[priority] is not provided.")
    @Column(nullable = false)
    Long priority;
}
