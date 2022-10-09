package br.com.fiap.belive_backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@NoArgsConstructor
public class Appointment {
    @Indexed(unique = true, background = true)
    private String id;

    private Doctor doctor;

    private Customer customer;

    private AppointmentStatus appointmentStatus;

    private enum AppointmentStatus{
        OPEN, IN_PROGRESS, CANCELLED, CLOSED
    }
}
