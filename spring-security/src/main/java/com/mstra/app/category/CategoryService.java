package com.mstra.app.category;

import com.mstra.app.category.request.CategoryRequest;
import com.mstra.app.category.request.CategoryUpdateRequest;
import com.mstra.app.category.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    String createCategory(CategoryRequest request, String userId);
    void updateCategory(CategoryUpdateRequest request, String catId, String userId);
    List<CategoryResponse> findAllByOwner(String userId);
    CategoryResponse findCategoryById(String catId);
    void deleteCategoryById(String catId);
}
