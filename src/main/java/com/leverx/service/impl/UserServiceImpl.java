package com.leverx.service.impl;

import com.leverx.dto.UserDTO;
import com.leverx.entity.User;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.mapper.UserMapper;
import com.leverx.repository.UserRepository;
import com.leverx.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserDTO.Response.Public> findAll() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO.Response.Public> findById(Integer id) throws NoSuchEntityException {
        User persistedUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("There is no User with ID = %d", id)));
        return Optional.of(userMapper.toDto(persistedUser));

    }

    public Optional<UserDTO.Response.Public> findByEmail(String email) throws UsernameNotFoundException {
        User persistedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        return Optional.of(userMapper.toDto(persistedUser));
    }

    @Override
    public UserDTO.Response.Public save(UserDTO.Request.Create userDtoRequest) {
        User newUser = userMapper.toEntity(userDtoRequest);
        userRepository.save(newUser);
        return userMapper.toDto(newUser);
    }

    @Override
    public UserDTO.Response.Public update(UserDTO.Request.Update userDtoRequest) throws NoSuchEntityException {
        User persistedUser = userRepository.findById(userDtoRequest.getId())
                .orElseThrow(() ->
                        new NoSuchEntityException(String.format("There is no User with ID = %d", userDtoRequest.getId())));

        userMapper.updateEntity(userDtoRequest, persistedUser);
        userRepository.save(persistedUser);

        return userMapper.toDto(persistedUser);
    }

    @Override
    public void deleteById(Integer id) throws NoSuchEntityException {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new NoSuchEntityException(String.format("There is no User with ID = %d", id));
        }
    }
}
