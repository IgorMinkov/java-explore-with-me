package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.DataNotFoundException;
import ru.practicum.exception.handler.ConflictException;
import ru.practicum.utils.EntityCheckService;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
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
        if (!eventRepository.findByCategoryId(categoryId).isEmpty()) {
            throw new ConflictException(String.format(
                    "Категория с id: %s используется и не может быть удалена", categoryId));
        }
        categoryRepository.deleteById(categoryId);
    }

}
