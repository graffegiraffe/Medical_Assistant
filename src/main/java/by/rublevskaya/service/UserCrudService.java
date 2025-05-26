package by.rublevskaya.service;

import by.rublevskaya.dto.user.UserDto;
import by.rublevskaya.dto.user.UserUpdateDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.UserMapper;
import by.rublevskaya.model.Security;
import by.rublevskaya.model.User;
import by.rublevskaya.repository.SecurityRepository;
import by.rublevskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCrudService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final UserMapper userMapper;

    @Transactional
    public User createUser(UserDto dto) {
        log.info("Creating a new user with username: {}", dto.getUsername());
        if (userRepository.findByEmail(dto.getEmail()).isPresent() || userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new CustomException("A user with this email or username already exists.");
        }
        User user = userMapper.toEntity(dto);
        log.debug("Mapped UserDto to User entity: {}", user);
        User savedUser = userRepository.save(user);
        log.info("User successfully save. User ID: {}", savedUser.getId());

        Security security = new Security();
        security.setLogin(dto.getUsername());
        security.setPassword(dto.getPassword());
        security.setRole("USER");
        security.setUserId(savedUser.getId());
        securityRepository.save(security);
        log.info("User successfully created with ID: {}", savedUser.getId());
        return savedUser;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        log.info("Fetching all users.");
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new CustomException("No users found in the database");
        }
        log.info("Successfully fetched {} users.", users.size());
        return users;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        log.info("Fetching user with ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with ID {} not found.", id);
            return new CustomException("User with ID " + id + " not found.");
        });
        log.info("Successfully fetched user with ID: {}", id);
        return user;
    }

    @Transactional
    public User updateUser(Long id, UserDto dto) {
        log.info("Updating user with ID: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(String.format("User with ID %d was not found", id)));


        if (!existingUser.getEmail().equals(dto.getEmail())) {
            userRepository.findByEmail(dto.getEmail())
                    .ifPresent(user -> {
                        throw new CustomException("A user with this email already exists");
                    });
            existingUser.setEmail(dto.getEmail());
        }

        if (!existingUser.getUsername().equals(dto.getUsername())) {
            userRepository.findByUsername(dto.getUsername())
                    .ifPresent(user -> {
                        throw new CustomException("A user with this username already exists");
                    });
            existingUser.setUsername(dto.getUsername());
        }

        existingUser.setBirthDate(dto.getBirthDate());
        existingUser.setBloodType(dto.getBloodType());
        User updatedUser = userRepository.save(existingUser);

        Security security = securityRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new CustomException("Security entry for user not found"));
        security.setLogin(dto.getUsername());
        security.setPassword(dto.getPassword());
        securityRepository.save(security);
        log.info("User successfully updated with ID: {}", updatedUser.getId());
        return updatedUser;
    }

    @Transactional
    public User partialUpdateUser(Long id, UserUpdateDto dto) {
        log.info("Partially updating user with ID: {}", id);
        User existingUser = getUserById(id);

        if (dto.getEmail() != null && !existingUser.getEmail().equals(dto.getEmail())) {
            userRepository.findByEmail(dto.getEmail())
                    .ifPresent(user -> {
                        throw new CustomException("A user with this email already exists");
                    });
            existingUser.setEmail(dto.getEmail());
        }

        if (dto.getUsername() != null && !existingUser.getUsername().equals(dto.getUsername())) {
            userRepository.findByUsername(dto.getUsername())
                    .ifPresent(user -> {
                        throw new CustomException("A user with this username already exists");
                    });
            existingUser.setUsername(dto.getUsername());
        }

        if (dto.getBirthDate() != null) {
            existingUser.setBirthDate(dto.getBirthDate());
        }

        if (dto.getBloodType() != null) {
            existingUser.setBloodType(dto.getBloodType());
        }

        User updatedUser = userRepository.save(existingUser);
        Security security = securityRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new CustomException("Security entry for user not found"));

        if (dto.getUsername() != null) {
            security.setLogin(dto.getUsername());
        }

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            security.setPassword(dto.getPassword());
        }

        securityRepository.save(security);
        log.info("User successfully partially updated with ID: {}", updatedUser.getId());
        return updatedUser;
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Attempting to delete user with ID: {}", id);
        User user = getUserById(id);
        securityRepository.findByLogin(user.getUsername())
                .ifPresent(securityRepository::delete);
        log.info("User with ID {} has been successfully deleted.", id);
        userRepository.delete(user);
    }
}