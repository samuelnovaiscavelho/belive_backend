package br.com.fiap.belive_backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
public class Company extends User {
    private String cnpj;

    private List<Doctor> doctorList = new ArrayList<>();
}
