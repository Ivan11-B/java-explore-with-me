package ru.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(name = "ids", required = false) List<Integer> ids,
                                            @RequestParam(defaultValue = "0") Integer from,
                                            @RequestParam(defaultValue = "10") Integer size) {
        List<UserDto> users = userService.getUsers(ids, from, size);
        log.info("Список пользователей получен");
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        UserDto userDto = userService.save(newUserRequest);
        log.info("Пользователь добавлен");
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        String text = userService.delete(userId);
        log.info("Пользователь удален");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(text);
    }
}
