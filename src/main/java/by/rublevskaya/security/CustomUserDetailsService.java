package by.rublevskaya.security;

import by.rublevskaya.repository.SecurityRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityRepository securityRepository;

    public CustomUserDetailsService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        by.rublevskaya.model.Security user = securityRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String roleWithPrefix = "ROLE_" + user.getRole().toUpperCase();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleWithPrefix);
        return new User(user.getLogin(), user.getPassword(), Collections.singletonList(authority));
    }
}
