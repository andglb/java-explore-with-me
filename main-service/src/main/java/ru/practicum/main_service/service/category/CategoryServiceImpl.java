package ru.practicum.main_service.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.dto.category.CategoryDto;
import ru.practicum.main_service.dto.category.NewCategoryDto;
import ru.practicum.main_service.entity.Category;
import ru.practicum.main_service.exceptions.CategoryIsNotEmptyException;
import ru.practicum.main_service.exceptions.CategoryNotExistException;
import ru.practicum.main_service.exceptions.NameAlreadyExistException;
import ru.practicum.main_service.mapper.CategoryMapper;
import ru.practicum.main_service.repository.CategoryRepository;
import ru.practicum.main_service.repository.EventRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        if (categoryRepository.existsByName(newCategoryDto.getName())) {
            throw new NameAlreadyExistException(String.format("Can't create category because name: %s already used by another category", newCategoryDto.getName()));
        }
        return categoryMapper.toCategoryDto(categoryRepository.save(categoryMapper.toCategory(newCategoryDto)));
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable page = PageRequest.of(from / size, size);
        return categoryMapper.toCategoryDtoList(categoryRepository.findAll(page).toList());
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotExistException("Category doesn't exist"));
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long catId) {
        if (eventRepository.existsByCategoryId(catId)) {
            throw new CategoryIsNotEmptyException("The category is not empty");
        }
        categoryRepository.deleteById(catId);
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotExistException("Category doesn't exist"));

        String newName = categoryDto.getName();

        if (!newName.equals(category.getName())) {
            if (categoryRepository.existsByName(newName)) {
                throw new NameAlreadyExistException(String.format("Can't update category because name: %s already used by another category", newName));
            }
        }
        category.setName(newName);
        return categoryMapper.toCategoryDto(categoryRepository.save(category));
    }
}