package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
