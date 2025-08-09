package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.NewUserRequest;
import ru.practicum.dto.UserDto;
import ru.practicum.exception.UserDuplicateException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.UserMapper;
import ru.practicum.model.User;
import ru.practicum.repository.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto save(NewUserRequest newUserRequest) {
        User user = userMapper.toEntity(newUserRequest);
        try {
            return userMapper.toDto(userRepository.save(user));
        } catch (DataIntegrityViolationException ex) {
            throw new UserDuplicateException("Email уже занят");
        }
    }

    @Override
    @Transactional
    public String delete(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User с id=" + userId + " не найден"));
        userRepository.deleteById(userId);
        return "Пользователь удален";
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        List<User> users;
        if (ids == null) {
            users = userRepository.findAllUsers(from, size);
        } else {
            users = userRepository.findUserByIds(ids, from, size);
        }
        if (users.isEmpty()) {
            return Collections.emptyList();
        } else {
            return userMapper.toDtoList(users);
        }
    }
}
