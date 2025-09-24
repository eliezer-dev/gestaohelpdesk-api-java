package dev.eliezer.superticket.modules.user.useCases;

import dev.eliezer.superticket.modules.user.dtos.UserForUpdateRequestDTO;
import dev.eliezer.superticket.modules.user.dtos.UserResponseDTO;
import dev.eliezer.superticket.modules.user.entities.User;
import dev.eliezer.superticket.modules.user.repositories.UserRepository;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dev.eliezer.superticket.modules.user.mappers.UserMapper.formatUserToUserResponseDTO;

@Service
public class UpdateUserUseCase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO execute(Long id, UserForUpdateRequestDTO userUpdate, Long userRole) throws AuthenticationException {
        Optional<User> userFound = userRepository.findByCpf(userUpdate.getCpf());
        if(userFound.isPresent() && userFound.get().getId() != id) {
            throw new BusinessException("[cpf] " + userUpdate.getCpf() + " já foi utilizado em outro cadastro.");
        }
        userFound = null;
        userFound = userRepository.findByEmail(userUpdate.getEmail());

        if(userFound.isPresent() && userFound.get().getId() != id) {
            throw new BusinessException("[email] " + userUpdate.getEmail() + " já foi utilizado em outro cadastro.");
        }

        User userToChange =  userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userToChange.setCpf(userUpdate.getCpf() != null ? userUpdate.getCpf() : userToChange.getCpf());
        userToChange.setName(userUpdate.getName() != null ? userUpdate.getName() : userToChange.getName());
        userToChange.setCep(userUpdate.getCep() != null ? userUpdate.getCep() : userToChange.getCep());
        userToChange.setAddress(userUpdate.getAddress() != null ? userUpdate.getAddress() : userToChange.getAddress());
        userToChange.setState(userUpdate.getState() != null ? userUpdate.getState() : userToChange.getState());
        userToChange.setCity(userUpdate.getCity() != null ? userUpdate.getCity() : userToChange.getCity());
        userToChange.setNeighborhood(userUpdate.getNeighborhood() != null ? userUpdate.getNeighborhood() : userToChange.getNeighborhood());
        userToChange.setAddressNumber(userUpdate.getAddressNumber() != null ? userUpdate.getAddressNumber() : userToChange.getAddressNumber());
        userToChange.setEmail(userUpdate.getEmail() != null ? userUpdate.getEmail() : userToChange.getEmail());
        userToChange.setAddressNumber2(userUpdate.getAddressNumber2() != null ? userUpdate.getAddressNumber2() : userToChange.getAddressNumber2());
        userToChange.setUserRole(userUpdate.getUserRole() != null && userRole == 2 ? userUpdate.getUserRole() : userToChange.getUserRole());


        if (userUpdate.getPassword() == null || userUpdate.getPassword().isEmpty()){
            userToChange.setPassword(userToChange.getPassword());
            User userSaved = userRepository.save(userToChange);
            return formatUserToUserResponseDTO(userSaved);
        }

        if (userUpdate.getOldPassword() == null || userUpdate.getOldPassword().isEmpty()) {
            throw new BusinessException("Senha atual não informada.");
        }

        var passwordMatches = this.passwordEncoder
                .matches(userUpdate.getOldPassword(), userToChange.getPassword());

        if (!passwordMatches){
            throw new AuthenticationException("E-mail ou senha incorretos.") {
            };
        }

        var passwordEncoded = passwordEncoder.encode(userUpdate.getPassword());
        userToChange.setPassword(passwordEncoded);

        userRepository.save(userToChange);
        return formatUserToUserResponseDTO(userToChange);
    }
}
