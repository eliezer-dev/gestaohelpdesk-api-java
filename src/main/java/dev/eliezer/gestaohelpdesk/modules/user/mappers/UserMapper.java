package dev.eliezer.gestaohelpdesk.modules.user.mappers;

import dev.eliezer.gestaohelpdesk.modules.user.dtos.UserRequestDTO;
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

    public static User formatUserRequestDTOToUser (UserRequestDTO userRequestDTO) {
        var user = User.builder()
                .cpf(userRequestDTO.cpf())
                .name(userRequestDTO.name())
                .cep(userRequestDTO.cep())
                .address(userRequestDTO.address())
                .addressNumber(userRequestDTO.addressNumber())
                .addressNumber2(userRequestDTO.addressNumber2())
                .city(userRequestDTO.city())
                .state(userRequestDTO.state())
                .neighborhood(userRequestDTO.neighborhood())
                .email(userRequestDTO.email())
                .idPicture(1L)
                .userRole(userRequestDTO.userRole())
                .build();
        return user;

    }


}
