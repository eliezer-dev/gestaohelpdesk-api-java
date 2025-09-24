package dev.eliezer.gestaohelpdesk.service;

import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;

public interface StatusService {
    Iterable<Status> findAll();
    Status findById(Long id);

    Status insert(Status status);

    Status update(Long id, Status status);

    void delete (Long id);
}
