package com.mstra.app.todo;

import com.mstra.app.common.RestResponse;
import com.mstra.app.todo.request.TodoRequest;
import com.mstra.app.todo.request.TodoUpdateRequest;
import com.mstra.app.todo.response.TodoResponse;
import com.mstra.app.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todos")
@Tag(name = "Todos",description = "Todo API")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<RestResponse> createTodo(
            @RequestBody @Valid final TodoRequest request, final Authentication authentication
    ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        final String todoId = this.todoService.createTodo(request, userId);
        return ResponseEntity.status(CREATED).body(new RestResponse(todoId));
    }

    @PutMapping("/{todo_id}")
    public ResponseEntity<Void> updateTodo(
            @RequestBody @Valid final TodoUpdateRequest request,
            @PathVariable("todo_id")final String todoId,
            final Authentication authentication
    ) {
        final String userId = ((User) authentication.getPrincipal()).getId();
        this.todoService.updateTodo(request, todoId, userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{todo_id}")
    public ResponseEntity<TodoResponse> findTodoById(@PathVariable("todo_id") final String todoId) {
        return ResponseEntity.ok(this.todoService.findTodoById(todoId));
    }

    @GetMapping("/today")
    public ResponseEntity<List<TodoResponse>> findAllTodoByUser(final Authentication authentication) {
        String userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(this.todoService.findAllTodosForToday(userId));
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<List<TodoResponse>> findAllTodosByCategory(
            @PathVariable("category_id") String categoryId,
            final Authentication authentication
    ) {
        String userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(this.todoService.findAllTodosByCategory(categoryId, userId));
    }

    @GetMapping("/due")
    public ResponseEntity<List<TodoResponse>> findAllDueTodos(
            final Authentication authentication
    ) {
        String userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(this.todoService.findAllDueTodos(userId));
    }

    @DeleteMapping("/{todo_id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("todo_id") final String todoId) {
        this.todoService.deleteTodoById(todoId);
        return ResponseEntity.ok().build();
    }
}
