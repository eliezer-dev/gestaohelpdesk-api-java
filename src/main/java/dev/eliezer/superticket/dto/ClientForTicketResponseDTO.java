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
public class ClientForTicketResponseDTO {
    @Schema(example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED, description = "id of client")
    Long id;
    @Schema(example = "98765432109876", requiredMode = Schema.RequiredMode.REQUIRED, description = "CPF or CNPJ of user")
    String cpfCnpj;
    @Schema(example = "Emporio Juca e Rosa Me", requiredMode = Schema.RequiredMode.REQUIRED, description = "company name or name of client")
    String razaoSocialName;
    @Schema(example = "87654321", requiredMode = Schema.RequiredMode.REQUIRED, description = "zip code of client")
    String cep;
    @Schema(example = "Rua Rio de Janeiro", requiredMode = Schema.RequiredMode.REQUIRED, description = "address of client")
    String address;
    @Schema(example = "456", requiredMode = Schema.RequiredMode.REQUIRED, description = "address number of client")
    String addressNumber;
    @Schema(example = "RJ", requiredMode = Schema.RequiredMode.REQUIRED, description = "state of client")
    String state;
    @Schema(example = "Rio de Janeiro", requiredMode = Schema.RequiredMode.REQUIRED, description = "city of client")
    String city;
    @Schema(example = "contato@emporiojucarosa.com.br", description = "email of client")
    String email;
}
