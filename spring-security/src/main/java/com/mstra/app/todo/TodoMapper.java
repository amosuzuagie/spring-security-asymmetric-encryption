package com.mstra.app.todo;

import com.mstra.app.category.response.CategoryResponse;
import com.mstra.app.todo.request.TodoRequest;
import com.mstra.app.todo.request.TodoUpdateRequest;
import com.mstra.app.todo.response.TodoResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TodoMapper {
    public Todo toTodo(TodoRequest request) {
        return Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .startTime(request.getStartTime())
                .endDate(request.getEndDate())
                .endTime(request.getEndTime())
                .done(false)
                .build();
    }

    public void margeTodo(Todo todoToUpdate, TodoUpdateRequest request) {
        if (StringUtils.isNoneBlank(request.getTitle()) &&
                !todoToUpdate.getTitle().equals(request.getTitle())
        ) {
            todoToUpdate.setTitle(request.getTitle());
        }
        if (StringUtils.isNoneBlank(request.getDescription()) &&
                !todoToUpdate.getDescription().equals(request.getDescription())
        ) {
            todoToUpdate.setDescription(request.getDescription());
        }
    }

    public TodoResponse toTodoResponse(final Todo todo) {
        return TodoResponse.builder()
                .todoId(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .startDate(todo.getStartDate())
                .startTime(todo.getStartTime())
                .endDate(todo.getEndDate())
                .endTime(todo.getEndTime())
                .done(todo.isDone())
                .category(
                        CategoryResponse.builder()
                                .name(todo.getCategory().getName())
                                .description(todo.getCategory().getDescription())
                                .build())
                .build();
    }
}
