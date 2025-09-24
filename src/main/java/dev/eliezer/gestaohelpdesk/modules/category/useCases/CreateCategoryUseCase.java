package dev.eliezer.gestaohelpdesk.modules.category.useCases;

import dev.eliezer.gestaohelpdesk.modules.category.entities.Category;
import dev.eliezer.gestaohelpdesk.modules.category.repositories.CategoryRepository;
import dev.eliezer.gestaohelpdesk.modules.category.services.validation.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryUseCase {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryValidator categoryValidator;

    public Category execute(Category category) {
        categoryValidator.validate(category);

        return categoryRepository.save(category);
    }


}
