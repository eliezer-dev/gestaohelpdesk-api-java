package dev.eliezer.superticket.modules.category.useCases;

import dev.eliezer.superticket.modules.category.entities.Category;
import dev.eliezer.superticket.modules.category.repositories.CategoryRepository;
import dev.eliezer.superticket.modules.category.services.validation.CategoryValidator;
import dev.eliezer.superticket.service.exception.BusinessException;
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
