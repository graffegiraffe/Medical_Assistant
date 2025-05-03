package by.rublevskaya.service;

import by.rublevskaya.dto.auth.UserRegistrationDto;
import by.rublevskaya.exception.CustomException;
import by.rublevskaya.mapper.UserMapper;
import by.rublevskaya.model.Security;
import by.rublevskaya.model.User;
import by.rublevskaya.repository.SecurityRepository;
import by.rublevskaya.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;
    private final UserMapper userMapper;

    @Transactional
    public void registerUser(UserRegistrationDto dto) {
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
    }
}