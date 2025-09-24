package dev.eliezer.gestaohelpdesk.modules.user.mappers;

import dev.eliezer.gestaohelpdesk.modules.user.dtos.UserResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.user.entities.User;
import lombok.Data;

@Data
public class UserMapper {

    public static UserResponseDTO formatUserToUserResponseDTO (User user) {
        var userResponse = UserResponseDTO.builder()
                .id(user.getId())
                .cpf(user.getCpf())
                .name(user.getName())
                .cep(user.getCep())
                .address(user.getAddress())
                .addressNumber(user.getAddressNumber())
                .addressNumber2(user.getAddressNumber2())
                .city(user.getCity())
                .state(user.getState())
                .neighborhood(user.getNeighborhood())
                .email(user.getEmail())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .userRole(user.getUserRole())
                .build();
        return userResponse;

    }


}
