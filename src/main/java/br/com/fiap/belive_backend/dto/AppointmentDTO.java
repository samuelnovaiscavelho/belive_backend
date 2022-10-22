package br.com.fiap.belive_backend.dto;

import br.com.fiap.belive_backend.model.Appointment;
import br.com.fiap.belive_backend.model.Appointment.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AppointmentDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    private BigInteger code;

    private DoctorDTO doctor;

    private CustomerDTO customer;

    private AppointmentStatus appointmentStatus;

    private CompanyDTO companyDTO;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private LocalDateTime startOfAppointment;

    public static Appointment toModel(AppointmentDTO appointmentDTO){
        return Appointment.builder()
                .id(appointmentDTO.getId())
                .code(appointmentDTO.getCode())
                .doctor(DoctorDTO.toModel(appointmentDTO.getDoctor()))
                .customer(CustomerDTO.toModel(appointmentDTO.getCustomer()))
                .appointmentStatus(appointmentDTO.getAppointmentStatus())
                .startOfAppointment(appointmentDTO.getStartOfAppointment())
                .build();
    }

    public static AppointmentDTO toDTO(Appointment appointment){
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .code(appointment.getCode())
                .doctor(DoctorDTO.toDTO(appointment.getDoctor()))
                .customer(CustomerDTO.toDTO(appointment.getCustomer()))
                .appointmentStatus(appointment.getAppointmentStatus())
                .startOfAppointment(appointment.getStartOfAppointment())
                .build();
    }
}
