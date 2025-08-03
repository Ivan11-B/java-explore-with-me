package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) List<Integer> ids,
                                            @RequestParam(required = false) Integer from,
                                            @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(userService.getUsers(ids, from, size));
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody NewUserRequest newUserRequest) {
        return ResponseEntity.ok(userService.save(newUserRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.delete(userId));
    }
}
