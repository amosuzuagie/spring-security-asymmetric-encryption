package com.mstra.app.category.impl;

import com.mstra.app.category.Category;
import com.mstra.app.category.CategoryMapper;
import com.mstra.app.category.CategoryRepository;
import com.mstra.app.category.CategoryService;
import com.mstra.app.category.request.CategoryRequest;
import com.mstra.app.category.request.CategoryUpdateRequest;
import com.mstra.app.category.response.CategoryResponse;
import com.mstra.app.exception.BusinessException;
import com.mstra.app.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public String createCategory(final CategoryRequest request, final String userId) {
        checkCategoryUnicityForUser(request.getName(), userId);
        final Category category = categoryMapper.toCategory(request);

        return this.categoryRepository.save(category).getId();
    }

    @Override
    public void updateCategory(final CategoryUpdateRequest request, final String catId, final String userId) {
        final Category categoryToUpdate = this.categoryRepository.findById(catId)
                        .orElseThrow(()-> new EntityNotFoundException(String.format("No category with ID: '%s'", catId)));

        checkCategoryUnicityForUser(request.getName(), userId);
        this.categoryMapper.margCategory(categoryToUpdate, request);
        this.categoryRepository.save(categoryToUpdate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllByOwner(final String userId) {
        return this.categoryRepository.findAllByUserId(userId)
                .stream()
                .map(this.categoryMapper::toCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse findCategoryById(final String catId) {
        return this.categoryRepository.findById(catId)
                .map(this.categoryMapper::toCategoryResponse)
                .orElseThrow(()-> new EntityNotFoundException(String.format("No category with ID: '%s'", catId)));
    }

    @Override
    public void deleteCategoryById(final String catId) {
        // TODO:. mark category for deletion
        // TODO:. Schedular should pick upp all the marked category and perform the deletion
    }

    private void checkCategoryUnicityForUser(String name, String userId) {
        final boolean alreadyExistForUser = this.categoryRepository.findByNameAndUser(name, userId);
        if (alreadyExistForUser) {
            throw new BusinessException(ErrorCode.CATEGORY_ALREADY_EXIST);
        }
    }
}
