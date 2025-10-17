package com.mstra.app.category;

import com.mstra.app.category.request.CategoryRequest;
import com.mstra.app.category.request.CategoryUpdateRequest;
import com.mstra.app.category.response.CategoryResponse;
import com.mstra.app.common.RestResponse;
import com.mstra.app.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
@Tag(name = "Categories", description = "Category API")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<RestResponse> createCategory(
            @Valid @RequestBody final CategoryRequest request, final Authentication authentication
    ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        final String catId = this.categoryService.createCategory(request, userId);
        return ResponseEntity.status(CREATED).body(new RestResponse(catId));
    }

//    @PreAuthorize()
    @PutMapping("/{category_id}")
    public ResponseEntity<Void> updateCategory(
            @Valid @RequestBody final CategoryUpdateRequest request,
            @PathVariable("category_id") final String categoryId,
            final Authentication authentication
    ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        this.categoryService.updateCategory(request, categoryId, userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategory(final Authentication authentication) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(this.categoryService.findAllByOwner(userId));
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryResponse> findCategoryById(@PathVariable("category_id") final String categoryId) {
        return ResponseEntity.ok(this.categoryService.findCategoryById(categoryId));
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<Void> deletedCategoryById(@PathVariable("category_id") String categoryId) {
        this.categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok().build();
    }
}
