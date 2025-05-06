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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCrudService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final UserMapper userMapper;

    @Transactional
    public User createUser(UserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent() || userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new CustomException("A user with this email or username already exists");
        }

        User user = userMapper.toEntity(dto);
        User savedUser = userRepository.save(user);

        Security security = new Security();
        security.setLogin(dto.getUsername());
        security.setPassword(dto.getPassword());
        security.setRole("USER");
        security.setUser(savedUser);
        securityRepository.save(security);

        return savedUser;
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User with ID " + id + " not found"));
    }

    @Transactional
    public User updateUser(Long id, UserDto dto) {
        User existingUser = getUserById(id);

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
        return updatedUser;
    }

    @Transactional
    public User partialUpdateUser(Long id, UserUpdateDto dto) {
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
        return updatedUser;
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        securityRepository.findByLogin(user.getUsername())
                .ifPresent(securityRepository::delete);
        userRepository.delete(user);
    }
}