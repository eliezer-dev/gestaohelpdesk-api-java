package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.dto.UserForUpdateRequestDTO;
import dev.eliezer.superticket.dto.UserResponseDTO;
import dev.eliezer.superticket.providers.EncryptUserPasswords;
import dev.eliezer.superticket.service.UserService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Iterable<UserResponseDTO> findAll() {
        List<UserResponseDTO> allUsersDTO = new ArrayList<>();
        userRepository.findAll().forEach(user -> {
//            var passwordEncoded = passwordEncoder.encode(user.getPassword());
//            user.setPassword(passwordEncoded);
//            userRepository.save(user);
            var userDTO = formatUserToUserResponseDTO(user);
            allUsersDTO.add(userDTO);
        });

       return allUsersDTO;
    }

    @Override
    public UserResponseDTO findById(Long id) {
        var userToFind = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
        return formatUserToUserResponseDTO(userToFind);
    }

    @Override
    public UserResponseDTO insert(User user) {
        Optional<User> userFound = userRepository.findByCpf(user.getCpf());
        if(userFound.isPresent()) {
            userFound = null;
            throw new BusinessException("[cpf] " + user.getCpf() + " já foi utilizado em outro cadastro.");
        }

        userFound = userRepository.findByEmail(user.getEmail());
        if(userFound.isPresent()) {
            throw new BusinessException("[email] " + user.getEmail() + " já foi utilizado em outro cadastro.");
        }

        var passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        var userToInsert = userRepository.save(user);
        return formatUserToUserResponseDTO(userToInsert);
    }

    @Override
    public UserResponseDTO update(Long id, UserForUpdateRequestDTO userUpdate) {
        Optional<User> userFound = userRepository.findByCpf(userUpdate.getCpf());
        if(userFound.isPresent() && userFound.get().getId() != id) {
            userFound = null;
            throw new BusinessException("[cpf] " + userUpdate.getCpf() + " já foi utilizado em outro cadastro.");
        }

        userFound = userRepository.findByEmail(userUpdate.getEmail());

        if(userFound.isPresent() && userFound.get().getId() != id) {
            throw new BusinessException("[email] " + userUpdate.getEmail() + " já foi utilizado em outro cadastro.");
        }

        User userToChange =  userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userToChange.setCpf(userUpdate.getCpf());
        userToChange.setName(userUpdate.getName());
        userToChange.setCep(userUpdate.getCep());
        userToChange.setState(userUpdate.getState());
        userToChange.setCity(userUpdate.getCity());
        userToChange.setAddress(userUpdate.getAddress());
        userToChange.setAddressNumber(userUpdate.getAddressNumber());
        userToChange.setEmail(userUpdate.getEmail());

        if (userUpdate.getNewPassword() == null){
            userToChange.setPassword(userToChange.getPassword());
            userRepository.save(userToChange);
            return formatUserToUserResponseDTO(userToChange);
        }

        if (userUpdate.getOldPassword() == null) {
            throw new BusinessException("Senha atual não informada.");
        }

        var passwordMatches = this.passwordEncoder
                .matches(userUpdate.getOldPassword(), userToChange.getPassword());

        if (!passwordMatches){
            throw new BusinessException("Usuário ou senha incorretos.");
        }

        var passwordEncoded = passwordEncoder.encode(userUpdate.getNewPassword());
        userToChange.setPassword(passwordEncoded);
        userRepository.save(userToChange);
        return formatUserToUserResponseDTO(userToChange);
    }

    @Override
    public void delete(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userRepository.delete(userToDelete);
    }

    public UserResponseDTO formatUserToUserResponseDTO (User user) {
        var userResponse = UserResponseDTO.builder()
                .id(user.getId())
                .cpf(user.getCpf())
                .name(user.getName())
                .cep(user.getCep())
                .address(user.getAddress())
                .addressNumber(user.getAddressNumber())
                .city(user.getCity())
                .state(user.getState())
                .email(user.getEmail())
                .createAt(user.getCreateAt())
                .build();
        return userResponse;

    }


}
