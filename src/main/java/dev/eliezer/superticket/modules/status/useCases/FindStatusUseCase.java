package dev.eliezer.superticket.modules.status.useCases;

import dev.eliezer.superticket.modules.status.entities.Status;
import dev.eliezer.superticket.modules.status.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FindStatusUseCase {
    @Autowired
    private StatusRepository statusRepository;

    public Iterable<Status> execute() {
        return statusRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

}
