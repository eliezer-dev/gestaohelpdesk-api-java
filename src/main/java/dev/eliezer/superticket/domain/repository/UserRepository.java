package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Iterable<User> findByNameIgnoreCaseContaining(String search);

    Iterable<User> findByCpfStartingWith(String search);

    @Query("SELECT u FROM tb_users u WHERE CAST(u.id AS string) LIKE CONCAT(:search, '%')")
    Iterable<User> findByIdStartingWith(String search);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailOrCpf(String email, String cpf);

    Optional<User> findByCpf(String cpf);

}
