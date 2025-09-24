package dev.eliezer.gestaohelpdesk.modules.user.useCases;

import dev.eliezer.gestaohelpdesk.modules.user.dtos.UserResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dev.eliezer.gestaohelpdesk.modules.user.mappers.UserMapper.formatUserToUserResponseDTO;

@Service
public class FindUserByIdUseCase {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO execute(Long id) {
        var userToFind = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        return formatUserToUserResponseDTO(userToFind);
    }
}
