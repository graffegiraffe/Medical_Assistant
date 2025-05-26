package by.rublevskaya.task;

import by.rublevskaya.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentCleanupTask {

    private final AppointmentService appointmentService;

    @Scheduled(cron = "0 0 0 * * ?") // Каждый день в полночь
    public void cleanOutdatedAppointments() {
        log.info("Appointment cleanup task started.");
        appointmentService.deleteOutdatedAppointments();
    }
}