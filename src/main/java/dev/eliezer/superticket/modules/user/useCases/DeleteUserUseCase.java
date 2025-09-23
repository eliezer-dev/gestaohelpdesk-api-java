package dev.eliezer.superticket.modules.user.useCases;

import dev.eliezer.superticket.modules.user.entities.User;
import dev.eliezer.superticket.modules.user.repositories.UserRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public void execute(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        userRepository.delete(userToDelete);
    }
}
