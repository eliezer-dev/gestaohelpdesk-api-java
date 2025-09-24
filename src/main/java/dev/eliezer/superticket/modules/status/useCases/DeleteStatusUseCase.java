package dev.eliezer.superticket.modules.status.useCases;

import dev.eliezer.superticket.modules.status.entities.Status;
import dev.eliezer.superticket.modules.status.repositories.StatusRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteStatusUseCase {

    @Autowired
    private StatusRepository statusRepository;

    public void execute(Long id) {
        Status statusToDelete = statusRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        statusRepository.delete(statusToDelete);
    }
}
