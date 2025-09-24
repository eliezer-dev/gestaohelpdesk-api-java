package dev.eliezer.superticket.modules.category.useCases;

import dev.eliezer.superticket.modules.category.entities.Category;
import dev.eliezer.superticket.modules.category.repositories.CategoryRepository;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCategoryUseCase {
    @Autowired
    private CategoryRepository categoryRepository;

    public void execute(Long id) {
        Category categoryToDelete = categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(id));

        categoryRepository.delete(categoryToDelete);
    }
}
