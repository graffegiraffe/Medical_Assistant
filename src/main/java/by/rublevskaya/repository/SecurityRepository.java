package by.rublevskaya.repository;

import by.rublevskaya.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityRepository extends JpaRepository<Security, Long> {
    boolean existsByLogin(String login);
    Optional<Security> findByLogin(String login);
}