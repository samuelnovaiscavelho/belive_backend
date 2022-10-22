package br.com.fiap.belive_backend.service;

import br.com.fiap.belive_backend.config.NullAwareBeanUtilsBean;
import br.com.fiap.belive_backend.dto.AppointmentDTO;
import br.com.fiap.belive_backend.dto.CustomerDTO;
import br.com.fiap.belive_backend.dto.DoctorDTO;
import br.com.fiap.belive_backend.model.Appointment;
import br.com.fiap.belive_backend.model.Appointment.AppointmentStatus;
import br.com.fiap.belive_backend.model.Company;
import br.com.fiap.belive_backend.model.Customer;
import br.com.fiap.belive_backend.model.Doctor;
import br.com.fiap.belive_backend.repository.AppointmentRepository;
import java.util.Comparator;
import java.util.Optional;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorService doctorService;

    private final CustomerServiceDefault customerServiceDefault;

    private final CompanyServiceDefault companyServiceDefault;

    private final NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    public AppointmentService(AppointmentRepository appointmentRepository, DoctorService doctorService, CustomerServiceDefault customerServiceDefault, CompanyServiceDefault companyServiceDefault, NullAwareBeanUtilsBean nullAwareBeanUtilsBean) {
        this.appointmentRepository = appointmentRepository;
        this.doctorService = doctorService;
        this.customerServiceDefault = customerServiceDefault;
        this.companyServiceDefault = companyServiceDefault;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
    }

    public List<Appointment> findAllByToken(String token){
        return customerServiceDefault.getUserByUsername(token).getAppointmentList();
    }

    public List<AppointmentDTO> findAllByTokenDTO(String token){
        return customerServiceDefault.getUserByUsername(token).getAppointmentList().stream()
                .map(AppointmentDTO::toDTO).collect(Collectors.toList());
    }

    public Appointment cancelAppointment(String token, Integer appointmentCode) {
        Appointment appointment = findAppointmentByCode(appointmentCode);

        if(appointment.getAppointmentStatus().equals(AppointmentStatus.CANCELLED)){
            throw new RuntimeException("Appointment already cancelled");
        }

        Customer customer = customerServiceDefault.getUserByUsername(token);
        Company company = companyServiceDefault.getByAppointmentCode(appointmentCode);
        Doctor doctor = appointment.getDoctor();

        customer.getAppointmentList().stream()
                .filter(appoint -> appoint.getCode().equals(BigInteger.valueOf(appointmentCode)))
                .findFirst()
                .ifPresentOrElse(appoint -> appoint.setAppointmentStatus(AppointmentStatus.CANCELLED),
                        () -> { throw new RuntimeException("Appointment not found"); });

        doctorService.findByCRM(company.getCnpj(), doctor.getCrm()).getScheduledAppointment()
                .removeIf(appoint -> appoint.getCode().equals(BigInteger.valueOf(appointmentCode)) && appoint.getCustomer().equals(customer));

        appointment.setAppointmentStatus(AppointmentStatus.CANCELLED);

        customerServiceDefault.updateCustomer(token, customer);

        doctorService.update(company.getCnpj(), doctor);

        return appointmentRepository.update(appointment);
    }

    @SneakyThrows
    public Appointment updateAppointment(String token, AppointmentDTO appointmentDTO, Integer appointmentCode) {
        Customer customer = customerServiceDefault.getUserByUsername(token);
        Doctor doctor = doctorService.findByCRM(appointmentDTO.getDoctor().getCrm());

        updateDataAppointmentDTO(appointmentDTO, doctor, customer);

        Appointment appointment = AppointmentDTO.toModel(appointmentDTO);

        appointment.setDoctor(doctor);

        validationAppointment(appointment);

        nullAwareBeanUtilsBean.copyProperties(appointment, appointmentRepository.findByCode(BigInteger.valueOf(appointmentCode)));

        customerServiceDefault.updateCustomer(token, customer);

        doctorService.update(appointmentDTO.getCompanyDTO().getCnpj(), doctor);

        return appointmentRepository.save(appointment);
    }

    public Appointment createAppointment(String token, AppointmentDTO appointmentDTO) {
        Doctor doctor = doctorService.findByCRM(appointmentDTO.getCompanyDTO().getCnpj(), appointmentDTO.getDoctor().getCrm());
        Customer customer = customerServiceDefault.getUserByUsername(token);

        updateDataAppointmentDTO(appointmentDTO, doctor, customer);

        Appointment appointment = AppointmentDTO.toModel(appointmentDTO);

        validationAppointment(appointment);

        appointment.setCode(BigInteger.valueOf(appointmentRepository.findAll().size()));

        appointment.setAppointmentStatus(AppointmentStatus.IN_PROGRESS);

        customer.getAppointmentList().add(appointment);

        doctor.getScheduledAppointment().add(appointment);

        customerServiceDefault.updateCustomer(token, customer);

        appointmentRepository.save(appointment);

        doctorService.update(appointmentDTO.getCompanyDTO().getCnpj(), doctor);

        return appointment;
    }

    private void updateDataAppointmentDTO(AppointmentDTO appointmentDTO, Doctor doctor, Customer customer) {
        appointmentDTO.setCustomer(CustomerDTO.toDTO(customer));
        appointmentDTO.setDoctor(DoctorDTO.toDTO(doctor));
    }

    private void validationAppointment(Appointment appointment) {
        Doctor doctor = appointment.getDoctor();
        LocalTime startOfAppointmentTime = LocalTime.from(appointment.getStartOfAppointment());
        LocalDate startOfAppointmentDate = LocalDate.from(appointment.getStartOfAppointment());

        if (startOfAppointmentTime.isBefore(doctor.getStartWork()) && startOfAppointmentTime.isAfter(doctor.getFinishWork())) {
            throw new RuntimeException("Appointment Date Unavailable");
        }

        if (LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), startOfAppointmentDate.getMonthValue(), startOfAppointmentDate.getDayOfMonth()).isBefore(LocalDate.now())) {
            throw new RuntimeException("Appointment cannot be scheduled with a past date");
        }
    }

    private Appointment findAppointmentByCode(Integer appointmentCode) {
        return appointmentRepository.findByCode(BigInteger.valueOf(appointmentCode))
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public Optional<AppointmentDTO> findNextAppointment(String token) {
        List<Appointment> appointmentList = findAllByToken(token).stream()
                .filter(appointment -> appointment.getAppointmentStatus().equals(AppointmentStatus.IN_PROGRESS))
                .collect(Collectors.toList());

        if(appointmentList.isEmpty()){
            return Optional.empty();
        }

        appointmentList = appointmentList.stream()
                .sorted(Comparator.comparing(Appointment::getStartOfAppointment))
                .collect(Collectors.toList());


        return Optional.ofNullable(appointmentList.get(0)).map(AppointmentDTO::toDTO);
    }
}
