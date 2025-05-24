package by.rublevskaya.task;

import by.rublevskaya.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentCleanupTask {

    private final AppointmentService appointmentService;

    @Scheduled(cron = "0 0 0 * * ?") // Каждый день в полночь
    public void cleanOutdatedAppointments() {
        appointmentService.deleteOutdatedAppointments();
    }
}