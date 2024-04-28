package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCpfCnpj(String search);
    Iterable<Client> findByCpfCnpjStartingWith(String search);

    @Query("SELECT c FROM tb_clients c WHERE CAST(c.id AS string) LIKE CONCAT(:search, '%')")
    Iterable<Client> findByIdStartingWith(String search);

    Iterable<Client> findByRazaoSocialNameIgnoreCaseContaining(String search);
    Iterable<Client> findByBusinessNameIgnoreCaseContaining(String search);
    Iterable<Client> findByEmailIgnoreCaseContaining(String search);
    Iterable<Client> findByCityIgnoreCaseContaining(String search);
    Iterable<Client> findByAddressIgnoreCaseContaining(String search);
}
