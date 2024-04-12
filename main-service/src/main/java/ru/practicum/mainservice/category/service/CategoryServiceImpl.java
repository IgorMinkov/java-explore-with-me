package ru.practicum.mainservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.CategoryRepository;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.utils.EntityCheckService;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityCheckService checkService;

    @Override
    @Transactional
    public Category create(Category category) {
        return null;
    }

    @Override
    @Transactional
    public Category update(Category category, Long categoryId) {
        return null;
    }

    @Override
    public Category getById(Long categoryId) {
        return null;
    }

    @Override
    public List<Category> getList(Integer from, Integer size) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {

    }

}
