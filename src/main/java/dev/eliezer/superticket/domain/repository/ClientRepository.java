package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
