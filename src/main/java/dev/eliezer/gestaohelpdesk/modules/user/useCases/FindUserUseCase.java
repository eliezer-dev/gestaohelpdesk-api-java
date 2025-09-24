package dev.eliezer.gestaohelpdesk.modules.user.useCases;

import dev.eliezer.gestaohelpdesk.modules.user.dtos.UserResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.eliezer.gestaohelpdesk.modules.user.mappers.UserMapper.formatUserToUserResponseDTO;

@Service
public class FindUserUseCase {
    @Autowired
    private UserRepository userRepository;


    public Iterable<UserResponseDTO> execute(String search, Long typeSearch) {
        List<UserResponseDTO> allUsersDTO = new ArrayList<>();
        if (search.isEmpty()) {
            userRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).forEach(user -> {
                var userDTO = formatUserToUserResponseDTO(user);
                allUsersDTO.add(userDTO);
            });
            return allUsersDTO;

        }else {
            if (typeSearch == 1) {
                userRepository.findByNameIgnoreCaseContaining(search).forEach(user -> {
                    var userDTO = formatUserToUserResponseDTO(user);
                    allUsersDTO.add(userDTO);
                });
                return allUsersDTO;
            }
            if (typeSearch == 2) {
                userRepository.findByCpfStartingWith(search).forEach(user -> {
                    var userDTO = formatUserToUserResponseDTO(user);
                    allUsersDTO.add(userDTO);
                });
                return allUsersDTO;
            }
            if (typeSearch == 3) {
                userRepository.findByIdStartingWith(search).forEach(user -> {
                    var userDTO = formatUserToUserResponseDTO(user);
                    allUsersDTO.add(userDTO);
                });
                return allUsersDTO;
            }

        }
        return allUsersDTO;

    }
}
