package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.UserRepository;
import dev.eliezer.superticket.dto.UserResponseDTO;
import dev.eliezer.superticket.service.UserService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public User insert(User user) {
        Optional<User> email = userRepository.findByEmail(user.getEmail());
        if (email.isPresent()){
            throw new BusinessException("O e-mail já está cadastrado");
        }

        var passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User userToChange =  userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userToChange.setCpf(user.getCpf());
        userToChange.setName(user.getName());
        userToChange.setCep(user.getCep());
        userToChange.setState(user.getState());
        userToChange.setCity(user.getCity());
        return userRepository.save(userToChange);
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
