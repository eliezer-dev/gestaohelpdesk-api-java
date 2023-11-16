package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.Status;
import dev.eliezer.superticket.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
