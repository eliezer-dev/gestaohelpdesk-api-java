package dev.eliezer.gestaohelpdesk.modules.category.repositories;

import dev.eliezer.gestaohelpdesk.modules.category.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
