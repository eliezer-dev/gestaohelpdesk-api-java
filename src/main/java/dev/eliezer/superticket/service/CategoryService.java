package dev.eliezer.superticket.service;

import dev.eliezer.superticket.domain.model.Category;
import dev.eliezer.superticket.domain.model.Status;

public interface CategoryService {
    Iterable<Category> index();

    Category findById(Long id);

    Category insert(Category category);

    Category update(Long id, Category category);

    void delete (Long id);

}
