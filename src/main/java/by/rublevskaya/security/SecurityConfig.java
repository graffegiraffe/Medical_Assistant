package by.rublevskaya.security;

import by.rublevskaya.security.jwt.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
        log.info("SecurityConfig created with JWTFilter:");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/users").hasRole("ADMIN")
                        .requestMatchers("/actuator/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/doctors/**").permitAll()
                        .requestMatchers("/doctors/**").hasAnyRole("ADMIN", "DOCTOR")

                        .requestMatchers(HttpMethod.GET, "/clinics/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clinics").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/clinics/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/clinics/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/clinics/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/clinics/**").hasRole("ADMIN")

                        .requestMatchers("/medications/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/medications/**").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/medications").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/medications/**").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/medications/**").hasAnyRole("ADMIN", "DOCTOR")

                        .requestMatchers("/medicalrecords/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/medicalrecords/**").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/medicalrecords").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PUT, "/medicalrecords/**").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE, "/medicalrecords/**").hasAnyRole("ADMIN", "DOCTOR")

                        .requestMatchers("/healthmetrics/**").hasAnyRole("USER", "DOCTOR")

                        .requestMatchers(HttpMethod.GET,"/appointments/user/**").hasAnyRole("USER", "DOCTOR")
                        .requestMatchers(HttpMethod.POST,"/appointments").hasAnyRole("USER", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE,"/appointments/**").hasAnyRole("USER", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE,"/appointments/outdated").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.DELETE,"/appointments/outdated").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PATCH,"/appointments/**").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.PATCH,"/appointments/**/complete").hasAnyRole("ADMIN", "DOCTOR")

                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((req, res, ex) -> {
                            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\": \"UNAUTHORIZED\", \"message\": \"Token required\"}");
                        })
                        .accessDeniedHandler((req, res, ex) -> {
                            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\": \"FORBIDDEN\", \"message\": \"Access denied\"}");
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}