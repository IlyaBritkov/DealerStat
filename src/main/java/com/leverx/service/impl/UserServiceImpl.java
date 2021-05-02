package com.leverx.service.impl;

import com.leverx.dto.FeedbackDTO;
import com.leverx.dto.UserDTO;
import com.leverx.entity.User;
import com.leverx.enums.UserRole;
import com.leverx.exception_handling.exception.NoSuchEntityException;
import com.leverx.exception_handling.exception.UserSignUpException;
import com.leverx.mapper.UserMapper;
import com.leverx.repository.UserRepository;
import com.leverx.service.FeedbackService;
import com.leverx.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FeedbackService feedbackService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO.Response.Public> findAllApprovedTraders() {
        List<User> userList = userRepository.findAllByApprovedIsTrue()
                .stream()
                .filter(user -> user.getRole() == UserRole.TRADER)
                .collect(Collectors.toList());
        return userList.stream()
                .map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO.Response.Public> findApprovedTraderById(Integer id) throws NoSuchEntityException {
        User persistedUser = getIfApprovedTraderByIdExists(id);
        return Optional.of(userMapper.toDto(persistedUser));
    }

    public List<UserDTO.Response.Public> findAllNotApprovedTraders() {
        List<User> userList = userRepository.findAllByApprovedIsFalse()
                .stream()
                .filter(user -> user.getRole() == UserRole.TRADER)
                .collect(Collectors.toList());
        return userList.stream()
                .map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO.Response.Public> findNotApprovedTraderById(Integer id) throws NoSuchEntityException {
        User persistedUser = getIfNotApprovedTraderByIdExists(id);
        return Optional.of(userMapper.toDto(persistedUser));
    }

    public Optional<UserDTO.Response.Public> findByEmail(String email) throws UsernameNotFoundException {
        User persistedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        return Optional.of(userMapper.toDto(persistedUser));
    }

    @Override
    public List<FeedbackDTO.Response.Public> findAllFeedbacksByUser(Integer id) throws NoSuchEntityException {
        getIfApprovedTraderByIdExists(id);
        return feedbackService.findAllByUserId(id);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public FeedbackDTO.Response.Public findFeedbackByIdByUserIdUser(Integer userId, Integer feedbackId) throws NoSuchEntityException {
        getIfApprovedTraderByIdExists(userId);
        return feedbackService.findByIdAndUserId(feedbackId, userId).get();
    }

    @Override
    public UserDTO.Response.Public save(UserDTO.Request.Create userDtoRequest) throws UserSignUpException {
        Optional<User> optionalUser = userRepository.findByEmail(userDtoRequest.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserSignUpException("User with that email already exists");
        }

        String decryptedPassword = userDtoRequest.getPassword();
        String encryptedPassword = passwordEncoder.encode(decryptedPassword);
        userDtoRequest.setPassword(encryptedPassword);
        User newUser = userMapper.toEntity(userDtoRequest);
        userRepository.save(newUser);
        return userMapper.toDto(newUser);
    }

    @Override
    public UserDTO.Response.Public update(UserDTO.Request.Update userDtoRequest) throws NoSuchEntityException {
        User persistedUser = userRepository.findByIdAndApprovedTrue(userDtoRequest.getId()).orElseThrow(() ->
                new NoSuchEntityException(String.format("There is no User with ID = %d", userDtoRequest.getId())));

        userMapper.updateEntity(userDtoRequest, persistedUser);
        userRepository.save(persistedUser);

        return userMapper.toDto(persistedUser);
    }

    @Override
    public UserDTO.Response.Public approve(UserDTO.Request.Approve userDtoRequest) throws NoSuchEntityException {
        User persistedUser = getIfNotApprovedTraderByIdExists(userDtoRequest.getId());
        persistedUser.setApproved(true);
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

    private User getIfApprovedTraderByIdExists(Integer id) throws NoSuchEntityException {
        Optional<User> optionalUser = userRepository.findByIdAndApprovedTrue(id);
        if (optionalUser.isEmpty() || optionalUser.get().getRole() != UserRole.TRADER) {
            throw new NoSuchEntityException(String.format("There is no Trader with ID = %d", id));
        }
        return optionalUser.get();
    }

    private User getIfNotApprovedTraderByIdExists(Integer id) throws NoSuchEntityException {
        Optional<User> optionalUser = userRepository.findByIdAndApprovedFalse(id);
        if (optionalUser.isEmpty() || optionalUser.get().getRole() != UserRole.TRADER) {
            throw new NoSuchEntityException(String.format("There is no Trader with ID = %d", id));
        }
        return optionalUser.get();
    }
}
