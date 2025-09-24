package dev.eliezer.superticket.modules.status.useCases;

import dev.eliezer.superticket.modules.status.entities.Status;
import dev.eliezer.superticket.modules.status.repositories.StatusRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateStatusUseCase {

    @Autowired
    private StatusRepository statusRepository;

    public Status execute(Long id, Status status) {
        Status statusToChange =  statusRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        statusToChange.setDescription(status.getDescription());
        statusToChange.setType(status.getType());
        return statusRepository.save(statusToChange);
    }
}
