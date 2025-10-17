package com.mstra.app.category.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "VALIDATION.CATEGORY.NAME.NOT_BLANK")
    private String name;
    @NotBlank(message = "VALIDATION.CATEGORY.DESCRIPTION.NOT_BLANK")
    private String description;
}
