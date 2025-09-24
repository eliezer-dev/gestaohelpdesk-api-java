package dev.eliezer.gestaohelpdesk.service.impl;

import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import dev.eliezer.gestaohelpdesk.modules.status.repositories.StatusRepository;
import dev.eliezer.gestaohelpdesk.service.StatusService;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Iterable<Status> findAll() {
        return statusRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Status findById(Long id) {
        return statusRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Status insert(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status update(Long id, Status status) {
        Status statusToChange =  statusRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        statusToChange.setDescription(status.getDescription());
        statusToChange.setType(status.getType());
        return statusRepository.save(statusToChange);
    }

    @Override
    public void delete(Long id) {
        Status statusToDelete = statusRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        statusRepository.delete(statusToDelete);
    }

}
