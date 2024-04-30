package dev.eliezer.superticket.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;

@Data
@Entity(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "9",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of user")
    private Long id;

    @NotBlank(message = "[name] is not provided.")
    @Schema(example = "Paulo Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "name and last name of user")
    private String name;

    @NotBlank(message = "[cpf] is not provided.")
    @Pattern(regexp = "\\d{11}", message = "[cpf] needs 11 digits and cannot contain a dot or other special characters.")
    @Schema(example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED, description = "cpf of user")
    private String cpf;

    @Pattern(regexp = "\\d{8}", message = "[cep] needs 11 digits and cannot contain a dash or other special characters.")
    @NotBlank(message = "[cep] is not provided.")
    @Schema(example = "12345678", requiredMode = Schema.RequiredMode.REQUIRED, description = "zip code of user")
    private String cep;

    @NotBlank(message = "[address] is not provided.")
    @Schema(example = "Avenida Paulista", requiredMode = Schema.RequiredMode.REQUIRED, description = "address of user")
    private String address;

    @NotBlank(message = "[address number] is not provided.")
    @Schema(example = "123", requiredMode = Schema.RequiredMode.REQUIRED, description = "address number of user")
    private String addressNumber;

    @Schema(example = "Proximo a Praça das Bandeiras", description = "address number 2 of client ")
    String addressNumber2;

    @NotBlank(message = "[state] is not provided.")
    @Schema(example = "SP", requiredMode = Schema.RequiredMode.REQUIRED, description = "state of user")
    private String state;

    @NotBlank(message = "[city] is not provided.")
    @Schema(example = "Sao Paulo", requiredMode = Schema.RequiredMode.REQUIRED, description = "city of user")
    private String city;

    @NotBlank(message = "[neighborhood] is not provided.")
    @Schema(example = "Cidade Jardim", requiredMode = Schema.RequiredMode.REQUIRED, description = "neighborhood of client")
    String neighborhood;

    @Email(message = "[email] is invalid.")
    @Schema(example = "paulo@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "email of user")
    private String email;

    @Length(min = 8, max = 100, message = "[password] length must be between 8 and 100 characters")
    @Schema(example = "senha@1234", requiredMode = Schema.RequiredMode.REQUIRED, description = "password of user", minLength = 8, maxLength = 30)
    private String password;

    @Schema(example = "ABCDEFG12345-minhafoto.jpg", description = "avatar do usuário")
    private String avatar;

//    @OneToOne(fetch = FetchType.EAGER)
//    private UserPicture userPicture;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(name = "id_picture")
    private Long idPicture;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_picture", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private UserPicture userPicture;



}