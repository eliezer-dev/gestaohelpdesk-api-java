package dev.eliezer.superticket.service;

import dev.eliezer.superticket.modules.status.entities.Status;

public interface StatusService {
    Iterable<Status> findAll();
    Status findById(Long id);

    Status insert(Status status);

    Status update(Long id, Status status);

    void delete (Long id);
}
