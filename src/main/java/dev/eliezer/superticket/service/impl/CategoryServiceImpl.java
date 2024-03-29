package dev.eliezer.superticket.service.impl;

import dev.eliezer.superticket.domain.model.Category;
import dev.eliezer.superticket.domain.repository.CategoryRepository;
import dev.eliezer.superticket.service.CategoryService;
import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Iterable<Category> index() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Category insert(Category category) {
        categoryValidator(category);

        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
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

    @Override
    public void delete(Long id) {
        Category categoryToDelete = categoryRepository
                                    .findById(id)
                                    .orElseThrow(() -> new NotFoundException(id));

        categoryRepository.delete(categoryToDelete);
    }

    public void categoryValidator(Category category) {
        if (category.getDescription() == null) {
            throw new BusinessException("Description is not provided.");
        }
        if (category.getPriority() == null) {
            throw new BusinessException("Priority is not provided.");
        }
    }
}
