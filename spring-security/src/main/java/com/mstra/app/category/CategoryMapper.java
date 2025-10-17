package com.mstra.app.category;

import com.mstra.app.category.request.CategoryRequest;
import com.mstra.app.category.request.CategoryUpdateRequest;
import com.mstra.app.category.response.CategoryResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public Category toCategory(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public void margCategory(Category categoryToUpdate, CategoryUpdateRequest request) {
        if (StringUtils.isNoneBlank(request.getName())
                && !categoryToUpdate.getName().equals(request.getName())
        ) {
            categoryToUpdate.setName(request.getName());
        }
        if (StringUtils.isNoneBlank(request.getDescription())
                && !categoryToUpdate.getDescription().equals(request.getDescription())
        ) {
            categoryToUpdate.setDescription(request.getDescription());
        }
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .todoCount(category.getTodos().size())
                .build();
    }
}
