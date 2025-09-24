package dev.eliezer.gestaohelpdesk.modules.user.useCases;

import dev.eliezer.gestaohelpdesk.modules.user.dtos.UserResponseDTO;
import dev.eliezer.gestaohelpdesk.modules.user.entities.User;
import dev.eliezer.gestaohelpdesk.modules.user.repositories.UserRepository;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.eliezer.gestaohelpdesk.modules.user.mappers.UserMapper.formatUserToUserResponseDTO;

@Service
public class CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(User user) {
        Optional<User> userFound = userRepository.findByCpf(user.getCpf());
        if(userFound.isPresent()) {
            userFound = null;
            throw new BusinessException("[cpf] " + user.getCpf() + " já foi utilizado em outro cadastro.");
        }

        userFound = userRepository.findByEmail(user.getEmail());
        if(userFound.isPresent()) {
            throw new BusinessException("[email] " + user.getEmail() + " já foi utilizado em outro cadastro.");
        }

        if (user.getPassword() == null) {
            throw new BusinessException("Senha não informada");
        }
        var passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        var userToInsert = userRepository.save(user);
        return formatUserToUserResponseDTO(userToInsert);
    }
}
