package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByCpfCnpj(String search);
    Iterable<Client> findByRazaoSocialNameIgnoreCaseContaining(String search);
    Iterable<Client> findByBusinessNameIgnoreCaseContaining(String search);
    Iterable<Client> findByEmailIgnoreCaseContaining(String search);
    Iterable<Client> findByCityIgnoreCaseContaining(String search);
    Iterable<Client> findByAddressIgnoreCaseContaining(String search);
}
