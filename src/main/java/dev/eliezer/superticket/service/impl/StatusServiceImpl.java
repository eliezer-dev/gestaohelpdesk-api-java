package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.domain.model.User;
import dev.eliezer.superticket.domain.repository.StatusRepository;
import dev.eliezer.superticket.service.StatusService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Iterable<Status> findAll() {
        return statusRepository.findAll();
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
        return statusRepository.save(statusToChange);
    }

    @Override
    public void delete(Long id) {
        Status statusToDelete = statusRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        statusRepository.delete(statusToDelete);
    }

}
