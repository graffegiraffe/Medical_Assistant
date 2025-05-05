package by.rublevskaya.service;

import by.rublevskaya.dto.user.UserCreateDto;
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
    public User createUser(UserCreateDto dto) {
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
    public User updateUser(Long id, UserCreateDto dto) {
        User existingUser = getUserById(id);

        userRepository.findByEmail(dto.getEmail())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new CustomException("A user with this email already exists");
                });

        userRepository.findByUsername(dto.getUsername())
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new CustomException("A user with this username already exists");
                });

        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());
        existingUser.setBirthDate(dto.getBirthDate());
        existingUser.setBloodType(dto.getBloodType());
        userRepository.save(existingUser);

        Security security = securityRepository.findByLogin(existingUser.getUsername())
                .orElseThrow(() -> new CustomException("Security entry for user not found"));

        security.setLogin(dto.getUsername());
        security.setPassword(dto.getPassword());
        securityRepository.save(security);

        return existingUser;
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        securityRepository.findByLogin(user.getUsername())
                .ifPresent(securityRepository::delete);
        userRepository.delete(user);
    }
}