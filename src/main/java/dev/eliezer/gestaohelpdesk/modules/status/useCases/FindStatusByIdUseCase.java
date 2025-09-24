package dev.eliezer.gestaohelpdesk.modules.status.useCases;

import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import dev.eliezer.gestaohelpdesk.modules.status.repositories.StatusRepository;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindStatusByIdUseCase {

    @Autowired
    private StatusRepository statusRepository;

    public Status execute(Long id) {
        return statusRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
