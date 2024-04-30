package dev.eliezer.superticket.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_user_pictures")
public class UserPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "9",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of picture")
    private Long id;

    @NotBlank(message = "[file] is not provided.")
    @Schema(example = "fotoJose.jpg", requiredMode = Schema.RequiredMode.REQUIRED, description = "original filename of picture")
    private String filename;

    @NotBlank(message = "[type] is not provided.")
    @Schema(example = "", requiredMode = Schema.RequiredMode.REQUIRED, description = "type of picture")
    private String type;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;
}
