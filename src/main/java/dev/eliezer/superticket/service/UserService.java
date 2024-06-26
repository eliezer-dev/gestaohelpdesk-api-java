package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.dto.UserForUpdateRequestDTO;
import dev.eliezer.superticket.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    Iterable<UserResponseDTO> index(String search, Long typeSearch);
    UserResponseDTO findById(Long id);

    UserResponseDTO insert(User user);

    UserResponseDTO update(Long id, UserForUpdateRequestDTO user, Long userRole);

    void delete (Long id);
    
}
