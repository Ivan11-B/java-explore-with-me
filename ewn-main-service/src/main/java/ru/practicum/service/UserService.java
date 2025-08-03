package ru.practicum.service;

import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(NewUserRequest newUserRequest);

    String delete(Integer userId);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);
}
