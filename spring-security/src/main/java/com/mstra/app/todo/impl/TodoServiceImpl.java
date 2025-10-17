package com.mstra.app.todo.impl;

import com.mstra.app.category.Category;
import com.mstra.app.category.CategoryRepository;
import com.mstra.app.todo.Todo;
import com.mstra.app.todo.TodoMapper;
import com.mstra.app.todo.TodoRepository;
import com.mstra.app.todo.TodoService;
import com.mstra.app.todo.request.TodoRequest;
import com.mstra.app.todo.request.TodoUpdateRequest;
import com.mstra.app.todo.response.TodoResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public String createTodo(TodoRequest request, String userId) {
        final Category category = checkAndReturnCategory(request.getCategoryId(), userId);
        final Todo todo = todoMapper.toTodo(request);

        return todoRepository.save(todo).getId();
    }

    @Override
    public void updateTodo(TodoUpdateRequest request, String todoId, String userId) {
        final Category category = checkAndReturnCategory(request.getCategoryId(), userId);
        final Todo todoToUpdate = todoRepository.findById(todoId)
                .orElseThrow(()-> new EntityNotFoundException(String.format("Todo not found with ID: '%s'", todoId)));

        this.todoMapper.margeTodo(todoToUpdate, request);
        todoToUpdate.setCategory(category);

        this.todoRepository.save(todoToUpdate);
    }

    @Override
    public TodoResponse findTodoById(String todoId) {
        return this.todoRepository.findById(todoId)
                .map(this.todoMapper::toTodoResponse)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No todo found with ID: '%s'", todoId)));
    }

    @Override
    public List<TodoResponse> findAllTodosForToday(String userId) {
        return this.todoRepository.findAllByUserId(userId)
                .stream()
                .map(this.todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public List<TodoResponse> findAllTodosByCategory(String catId, String userId) {
        return this.todoRepository.findAllByUserIdAndCategoryId(userId, catId)
                .stream()
                .map(this.todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public List<TodoResponse> findAllDueTodos(String userId) {
        return this.todoRepository.findAllDueTodos(userId)
                .stream()
                .map(this.todoMapper::toTodoResponse)
                .toList();
    }

    @Override
    public void deleteTodoById(String todoId) {
        this.todoRepository.deleteById(todoId);
    }

    private Category checkAndReturnCategory(final String catId, final String userId) {
        return this.categoryRepository.findByIdAndUserId(catId, userId)
                .orElseThrow(()-> new EntityNotFoundException(String.format("No category with ID '%s'", catId)));
    }
}
