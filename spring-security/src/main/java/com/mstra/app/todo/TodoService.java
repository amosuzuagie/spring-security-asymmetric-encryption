package com.mstra.app.todo;

import com.mstra.app.todo.request.TodoRequest;
import com.mstra.app.todo.request.TodoUpdateRequest;
import com.mstra.app.todo.response.TodoResponse;

import java.util.List;

public interface TodoService {
    String createTodo(TodoRequest request, String userId);
    void updateTodo(TodoUpdateRequest request, String todoId, String userId);
    TodoResponse findTodoById(String todoId);
    List<TodoResponse> findAllTodosForToday(String userId);
    List<TodoResponse> findAllTodosByCategory(String catId, String userId);
    List<TodoResponse> findAllDueTodos(String userId);
    void deleteTodoById(String todoId);
}
