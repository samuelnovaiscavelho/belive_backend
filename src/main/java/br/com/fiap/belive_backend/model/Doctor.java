package br.com.fiap.belive_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Doctor {
    private Integer crm;

    private String name;

    private String speciality;

    private List<Appointment> scheduledAppointment = new ArrayList<>();

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startWork;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime finishWork;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="America/Sao_Paulo")
    private List<LocalDateTime> avaliableSchedule = new ArrayList<>();
}
