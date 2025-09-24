package dev.eliezer.superticket.modules.status.repositories;

import dev.eliezer.superticket.modules.status.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
