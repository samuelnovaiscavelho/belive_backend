package br.com.fiap.belive_backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
public class Customer extends User {
    private String cpf;

    private List<Appointment> appointmentList = new ArrayList<>();
}
