package dev.eliezer.gestaohelpdesk.modules.category.useCases;

import dev.eliezer.gestaohelpdesk.modules.category.entities.Category;
import dev.eliezer.gestaohelpdesk.modules.category.repositories.CategoryRepository;
import dev.eliezer.gestaohelpdesk.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindCategoryByIdUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category execute(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
}
