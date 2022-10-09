package br.com.fiap.belive_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Doctor {

    private Integer crm;

    private String name;

    private String speciality;
}
