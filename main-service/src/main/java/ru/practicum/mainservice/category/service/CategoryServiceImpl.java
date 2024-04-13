package ru.practicum.mainservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.CategoryRepository;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.exception.DataNotFoundException;
import ru.practicum.mainservice.utils.EntityCheckService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityCheckService checkService;

    @Override
    @Transactional
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category update(Category category, Long categoryId) {
        Category updatedCategory = getById(categoryId);
        Optional.ofNullable(category.getName()).ifPresent(updatedCategory::setName);
        categoryRepository.save(updatedCategory);
        return updatedCategory;
    }

    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException(
                        String.format("Не найдена категория c id: %s", categoryId)));
    }

    @Override
    public List<Category> getList(Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageRequest).toList();
    }

    @Override
    @Transactional
    public void delete(Long categoryId) {
        checkService.checkCategory(categoryId);
        categoryRepository.deleteById(categoryId);
    }

}
