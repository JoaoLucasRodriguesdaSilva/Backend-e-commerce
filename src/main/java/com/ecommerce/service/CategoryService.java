package com.ecommerce.service;

import com.ecommerce.dto.CategoryRequest;
import com.ecommerce.dto.CategoryResponse;
import com.ecommerce.entity.Category;
import com.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<CategoryResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoryResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional
    public CategoryResponse create(CategoryRequest dto) {
        Category parent = dto.parentCategoryId() != null
            ? repository.findById(dto.parentCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent category not found"))
            : null;
        Category entity = Category.builder()
            .name(dto.name())
            .slug(dto.slug())
            .parentCategory(parent)
            .icon(dto.icon())
            .displayOrder(dto.displayOrder())
            .build();
        return toResponse(repository.save(entity));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest dto) {
        Category entity = getOrThrow(id);
        Category parent = dto.parentCategoryId() != null
            ? repository.findById(dto.parentCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent category not found"))
            : null;
        entity.setName(dto.name());
        entity.setSlug(dto.slug());
        entity.setParentCategory(parent);
        entity.setIcon(dto.icon());
        entity.setDisplayOrder(dto.displayOrder());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Category getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found: " + id));
    }

    private CategoryResponse toResponse(Category e) {
        return new CategoryResponse(e.getId(), e.getName(), e.getSlug(),
            e.getParentCategory() != null ? e.getParentCategory().getId() : null,
            e.getIcon(), e.getDisplayOrder());
    }
}
