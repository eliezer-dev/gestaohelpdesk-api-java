package dev.eliezer.gestaohelpdesk.dto;

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
    @Schema(example = "Emporio Juca", description = "business name or name of client")
    String businessName;
    @Schema(example = "87654321", requiredMode = Schema.RequiredMode.REQUIRED, description = "zip code of client")
    String cep;
    @Schema(example = "Rua Rio de Janeiro", requiredMode = Schema.RequiredMode.REQUIRED, description = "address of client")
    String address;
    @Schema(example = "456", requiredMode = Schema.RequiredMode.REQUIRED, description = "address number of client")
    String addressNumber;
    @Schema(example = "Cidade Jardim", requiredMode = Schema.RequiredMode.REQUIRED, description = "neighborhood of client")
    String neighborhood;
    @Schema(example = "RJ", requiredMode = Schema.RequiredMode.REQUIRED, description = "state of client")
    String state;
    @Schema(example = "Rio de Janeiro", requiredMode = Schema.RequiredMode.REQUIRED, description = "city of client")
    String city;
    @Schema(example = "contato@emporiojucarosa.com.br", description = "email of client")
    String email;

    @Schema(example = "4", requiredMode = Schema.RequiredMode.REQUIRED, description = "time in hours of the sla contract in default cases")
    Long slaDefault;

    @Schema(example = "4", requiredMode = Schema.RequiredMode.REQUIRED, description = "time in hours of the sla contract in urgent cases")
    Long slaUrgency;
}
