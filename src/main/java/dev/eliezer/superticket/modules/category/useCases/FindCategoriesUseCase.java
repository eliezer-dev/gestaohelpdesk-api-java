package dev.eliezer.superticket.modules.category.useCases;

import dev.eliezer.superticket.modules.category.entities.Category;
import dev.eliezer.superticket.modules.category.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FindCategoriesUseCase {

    @Autowired
    private CategoryRepository categoryRepository;

    public Iterable<Category> execute() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
