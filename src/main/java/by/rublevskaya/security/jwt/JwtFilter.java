package by.rublevskaya.security.jwt;

import by.rublevskaya.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        log.info("JwtFilter created");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && jwtUtil.validateToken(token)) {
            try {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);
                Long userIdFromToken = jwtUtil.extractUserId(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (!userDetails.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals(role))) {
                    log.error("Role in token ({}) does not match user's role", role);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"FORBIDDEN\", \"message\": \"Invalid role in token\"}");
                    return;
                }

                validateUserIdAccess(request, userIdFromToken);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("User {} authenticated successfully with role {}", username, role);

            } catch (SecurityException ex) {
                log.error("Access denied: {}", ex.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"FORBIDDEN\", \"message\": \"" + ex.getMessage() + "\"}");
                return;
            } catch (Exception ex) {
                log.error("Error while setting user authentication: {}", ex.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

    private void validateUserIdAccess(HttpServletRequest request, Long userIdFromToken) {
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/users/") || requestURI.contains("/doctors/")) {
            String[] uriParts = requestURI.split("/");
            String userIdFromPath = uriParts[uriParts.length - 1];
            try {
                if (!userIdFromPath.equals(userIdFromToken.toString())) {
                    log.error("User with ID {} attempted to modify data for ID {}", userIdFromToken, userIdFromPath);
                    throw new SecurityException("You are not allowed to modify data for other users or doctors.");
                }
            } catch (NumberFormatException e) {
                log.error("Invalid userId format in path: {}", userIdFromPath);
                throw new SecurityException("Invalid userId format in request.");
            }
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            log.info("Extracted token: {}", token.substring(0, Math.min(token.length(), 10)) + "...");
            return token;
        }
        log.warn("No valid Authorization header found");
        return null;
    }
}