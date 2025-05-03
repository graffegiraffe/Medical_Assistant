package by.rublevskaya.repository;

import by.rublevskaya.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Security, Long> {

    Optional<Security> findByLogin(String login);

}