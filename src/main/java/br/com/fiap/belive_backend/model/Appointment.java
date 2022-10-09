package br.com.fiap.belive_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Appointment {
    @Indexed(unique = true, background = true)
    private String id;

    private Doctor doctor;

    private Customer customer;

    private AppointmentStatus appointmentStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime startOfAppointment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime endOfAppointment;

    private enum AppointmentStatus{
        OPEN, IN_PROGRESS, CANCELLED, CLOSED
    }
}
