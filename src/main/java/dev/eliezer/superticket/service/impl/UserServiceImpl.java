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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.AuthenticationException;
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
    public Iterable<UserResponseDTO> index(String search, Long typeSearch) {
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

        if (user.getPassword() == null) {
            throw new BusinessException("Senha não informada");
        }
        var passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        var userToInsert = userRepository.save(user);
        return formatUserToUserResponseDTO(userToInsert);
    }

    @Override
    public UserResponseDTO update(Long id, UserForUpdateRequestDTO userUpdate) throws AuthenticationException {
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
                .addressNumber2(user.getAddressNumber2())
                .city(user.getCity())
                .state(user.getState())
                .neighborhood(user.getNeighborhood())
                .email(user.getEmail())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .build();
        return userResponse;

    }

}
