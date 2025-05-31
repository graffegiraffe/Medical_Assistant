package by.rublevskaya.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder
                .withDetail("app", Map.of(
                        "name", "Pet-project",
                        "description", "Medical Assistant Management App",
                        "version", "1.0.0"
                ))
                .withDetail("contact", Map.of(
                        "email", "katerublevsk@icloud.com"
                ));
    }
}