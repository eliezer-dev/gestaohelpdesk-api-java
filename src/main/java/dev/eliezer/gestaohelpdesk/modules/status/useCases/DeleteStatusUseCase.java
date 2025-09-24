package dev.eliezer.gestaohelpdesk.modules.status.useCases;

import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import dev.eliezer.gestaohelpdesk.modules.status.repositories.StatusRepository;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
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
