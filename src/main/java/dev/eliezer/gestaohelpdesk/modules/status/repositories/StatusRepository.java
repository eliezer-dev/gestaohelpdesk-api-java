package dev.eliezer.gestaohelpdesk.modules.status.repositories;

import dev.eliezer.gestaohelpdesk.modules.status.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
