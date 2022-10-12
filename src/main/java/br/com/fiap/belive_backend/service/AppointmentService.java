package br.com.fiap.belive_backend.service;

import br.com.fiap.belive_backend.model.Appointment;
import br.com.fiap.belive_backend.model.Appointment.AppointmentStatus;
import br.com.fiap.belive_backend.model.Customer;
import br.com.fiap.belive_backend.model.Doctor;
import br.com.fiap.belive_backend.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorService doctorService;

    private final CustomerServiceDefault customerServiceDefault;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorService doctorService, CustomerServiceDefault customerServiceDefault) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.customerServiceDefault = customerServiceDefault;
    }

    public Appointment createAppointment(String token, LocalDateTime dateTimeMedicalAppointment, String cnpj, Integer crm) {
        Doctor doctor = doctorService.findByCRM(cnpj, crm);
        Customer customer = customerServiceDefault.getUserByUsername(token);

        Appointment appointment = Appointment
                .builder()
                .startOfAppointment(dateTimeMedicalAppointment)
                .doctor(doctor)
                .customer(customer)
                .appointmentStatus(AppointmentStatus.IN_PROGRESS)
                .build();

        customer.getAppointmentList().add(appointment);

        doctor.getScheduledAppointment().add(appointment);

        customerServiceDefault.updateCustomer(token, customer);

        appointmentRepository.save(appointment);

        doctorService.update(cnpj, doctor);

        return appointment;
    }
}
