package dev.eliezer.superticket.domain.repository;

import dev.eliezer.superticket.domain.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
