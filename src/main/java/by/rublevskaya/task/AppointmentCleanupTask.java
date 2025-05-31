package by.rublevskaya.task;

import by.rublevskaya.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentCleanupTask {

    private final AppointmentService appointmentService;

    @Value("${app.scheduling.cleanup-cron}")
    private String cleanupCron;

    @Scheduled(cron = "${app.scheduling.cleanup-cron}")
    public void cleanOutdatedAppointments() {
        log.info("Appointment cleanup task started with cron expression: {}", cleanupCron);
        appointmentService.deleteOutdatedAppointments();
    }
}