package br.com.fiap.belive_backend.dto;

import br.com.fiap.belive_backend.model.Doctor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Integer crm;

    private String name;

    private String speciality;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startWork;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime finishWork;

    public static Doctor toModel(DoctorDTO doctorDTO) {
        return Doctor.builder()
                .crm(doctorDTO.getCrm())
                .name(doctorDTO.getName())
                .speciality(doctorDTO.getSpeciality())
                .startWork(doctorDTO.getStartWork())
                .finishWork(doctorDTO.getFinishWork())
                .build();
    }

    public static DoctorDTO toDTO(Doctor doctor){
        return DoctorDTO.builder()
                .crm(doctor.getCrm())
                .name(doctor.getName())
                .speciality(doctor.getSpeciality())
                .startWork(doctor.getStartWork())
                .finishWork(doctor.getFinishWork())
                .build();
    }
}
