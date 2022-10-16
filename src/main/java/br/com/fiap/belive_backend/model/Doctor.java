package br.com.fiap.belive_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Doctor {
    private Integer crm;

    private String name;

    private String speciality;

    private List<Appointment> scheduledAppointment = new ArrayList<>();

    @JsonFormat(pattern = "HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalTime startWork;

    @JsonFormat(pattern = "HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalTime finishWork;
}
