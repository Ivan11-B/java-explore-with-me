package ru.practicum.service;

import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.model.User;

import java.util.List;

public interface UserService {

    UserDto save(NewUserRequest newUserRequest);

    void delete(Integer userId);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    User getUserById(Integer userId);
}
