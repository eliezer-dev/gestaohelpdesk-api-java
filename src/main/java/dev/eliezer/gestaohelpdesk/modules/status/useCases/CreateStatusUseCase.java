package dev.eliezer.gestaohelpdesk.modules.status.useCases;

import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import dev.eliezer.gestaohelpdesk.modules.status.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateStatusUseCase {

    @Autowired
    private StatusRepository statusRepository;

    public Status execute(Status status) {
        return statusRepository.save(status);
    }
}
