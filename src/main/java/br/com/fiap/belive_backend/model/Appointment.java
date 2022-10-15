package br.com.fiap.belive_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Document(collection = "appointment")
public class Appointment {
    @Id
    private String id;

    private BigInteger code;

    private Doctor doctor;

    private Customer customer;

    private AppointmentStatus appointmentStatus;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime startOfAppointment;

    public enum AppointmentStatus{
        OPEN, IN_PROGRESS, CANCELLED, CLOSED
    }
}
