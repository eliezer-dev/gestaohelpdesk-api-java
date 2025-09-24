package dev.eliezer.superticket.modules.category.useCases;

import dev.eliezer.superticket.modules.category.entities.Category;
import dev.eliezer.superticket.modules.category.repositories.CategoryRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category execute(Long id, Category category) {
        Category categoryToUpdate = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        if (category.getDescription() != null) {
            categoryToUpdate.setDescription((category.getDescription()));
        }
        if (category.getPriority() != null) {
            categoryToUpdate.setPriority((category.getPriority()));
        }

        return categoryRepository.save(categoryToUpdate);
    }
}
