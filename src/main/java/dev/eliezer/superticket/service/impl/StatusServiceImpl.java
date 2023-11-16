package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.domain.repository.StatusRepository;
import dev.eliezer.superticket.service.StatusService;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    private StatusServiceImpl(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }
    @Override
    public Iterable<Status> findAll() {
        return statusRepository.findAll();
    }

    @Override
    public Status findById(Long id) {
        return statusRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Status insert(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public Status update(Long id, Status status) {
        Status statusToChange =  statusRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        statusToChange.setDescription(status.getDescription());
        return statusRepository.save(statusToChange);
    }

    @Override
    public void delete(Long id) {
        statusRepository.deleteById(id);
    }
}
