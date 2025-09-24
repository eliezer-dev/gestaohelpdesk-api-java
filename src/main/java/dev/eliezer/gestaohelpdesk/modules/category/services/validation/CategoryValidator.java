package dev.eliezer.gestaohelpdesk.modules.category.services.validation;

import dev.eliezer.gestaohelpdesk.modules.category.entities.Category;
import dev.eliezer.gestaohelpdesk.service.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class CategoryValidator {

    public void validate(Category category) {
        if (category.getDescription() == null) {
            throw new BusinessException("Description is not provided.");
        }
        if (category.getPriority() == null) {
            throw new BusinessException("Priority is not provided.");
        }
    }
}
