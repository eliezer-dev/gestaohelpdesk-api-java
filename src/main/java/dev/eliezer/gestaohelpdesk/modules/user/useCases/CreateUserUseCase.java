package dev.eliezer.gestaohelpdesk.modules.user.useCases;

import dev.eliezer.gestaohelpdesk.modules.user.dtos.UserRequestDTO;
import dev.eliezer.gestaohelpdesk.modules.user.dtos.UserResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.user.entities.User;
import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.eliezer.gestaohelpdesk.modules.user.mappers.UserMapper.formatUserRequestDTOToUser;
import static dev.eliezer.gestaohelpdesk.modules.user.mappers.UserMapper.formatUserToUserResponseDTO;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(UserRequestDTO userData) {

        Optional<User> userFound = userRepository.findByCpf(userData.cpf());

        if(userFound.isPresent()) {
            userFound = null;
            throw new BusinessException("[cpf] " + userData.cpf() + " já foi utilizado em outro cadastro.");
        }

        userFound = userRepository.findByEmail(userData.email());

        if(userFound.isPresent()) {
            throw new BusinessException("[email] " + userData.email() + " já foi utilizado em outro cadastro.");
        }

        if (userData.password() == null) {
            throw new BusinessException("Senha não informada");
        }

        var passwordEncoded = passwordEncoder.encode(userData.password());

        var userToSave = formatUserRequestDTOToUser(userData);
        userToSave.setPassword(passwordEncoded);

        var userSaved = userRepository.save(userToSave);

        return formatUserToUserResponseDTO(userSaved);
    }
}
