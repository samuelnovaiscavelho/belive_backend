package br.com.fiap.belive_backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@NoArgsConstructor
@Data
public class Doctor {
    @Indexed(unique = true, background = true)
    private String id;

    private String name;

    private Integer crm;

    private String speciality;

    private List<Appointment> appointmentList;
}
