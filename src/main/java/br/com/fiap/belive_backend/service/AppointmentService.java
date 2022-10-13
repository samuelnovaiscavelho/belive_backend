package br.com.fiap.belive_backend.service;

import br.com.fiap.belive_backend.dto.AppointmentDTO;
import br.com.fiap.belive_backend.dto.CustomerDTO;
import br.com.fiap.belive_backend.dto.DoctorDTO;
import br.com.fiap.belive_backend.model.Appointment;
import br.com.fiap.belive_backend.model.Appointment.AppointmentStatus;
import br.com.fiap.belive_backend.model.Customer;
import br.com.fiap.belive_backend.model.Doctor;
import br.com.fiap.belive_backend.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

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

    public Appointment createAppointment(String token, AppointmentDTO appointmentDTO) {
        Doctor doctor = doctorService.findByCRM(appointmentDTO.getCompanyDTO().getCnpj(), appointmentDTO.getDoctor().getCrm());
        Customer customer = customerServiceDefault.getUserByUsername(token);

        appointmentDTO.setCustomer(CustomerDTO.toDTO(customer));
        appointmentDTO.setDoctor(DoctorDTO.toDTO(doctor));

        Appointment appointment = AppointmentDTO.toModel(appointmentDTO);

        appointment.setAppointmentStatus(AppointmentStatus.IN_PROGRESS);

        customer.getAppointmentList().add(appointment);

        doctor.getScheduledAppointment().add(appointment);

        customerServiceDefault.updateCustomer(token, customer);

        appointmentRepository.save(appointment);

        doctorService.update(appointmentDTO.getCompanyDTO().getCnpj(), doctor);

        return appointment;
    }
}
