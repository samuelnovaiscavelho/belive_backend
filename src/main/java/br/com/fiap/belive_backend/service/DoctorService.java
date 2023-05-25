package br.com.fiap.belive_backend.service;

import br.com.fiap.belive_backend.config.NullAwareBeanUtilsBean;
import br.com.fiap.belive_backend.exception.UserNotFoundException;
import br.com.fiap.belive_backend.model.Appointment;
import br.com.fiap.belive_backend.model.Company;
import br.com.fiap.belive_backend.model.Doctor;
import br.com.fiap.belive_backend.utils.DateUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private CompanyServiceDefault companyService;

    private NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    private DoctorService(CompanyServiceDefault companyService, NullAwareBeanUtilsBean nullAwareBeanUtilsBean) {
        this.companyService = companyService;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
    }

    public List<Doctor> findAll(String token) {
        return companyService.getUserByUsername(token).getDoctorList();
    }

    public Doctor findByCRM(Integer crm) {
        return companyService.findAll().stream()
                .flatMap(company -> company.getDoctorList().stream())
                .filter(doctor -> doctor.getCrm().equals(crm))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException("Doctor not found"));
    }

    public Doctor findByCRM(String cnpj, Integer crm) {
        return companyService.getByCnpj(cnpj).getDoctorList().stream()
                .filter(doctor -> Objects.equals(doctor.getCrm(), crm))
                .findAny().orElseThrow(() -> new UserNotFoundException("Doctor not found"));
    }

    public Doctor findByCRMWithToken(String token, Integer crm) {
        return findAll(token).stream()
                .filter(doctor -> Objects.equals(doctor.getCrm(), crm))
                .findAny().orElseThrow(() -> new UserNotFoundException("Doctor not found"));
    }

    public List<Doctor> findAllBySpecialityWithToken(String token, String speciality) {
        return findAll(token).stream()
                .filter(doctor -> doctor.getSpeciality().equalsIgnoreCase(speciality))
                .collect(Collectors.toList());
    }

    public List<Doctor> findAllBySpeciality(String cnpj, String speciality) {
        return companyService.getByCnpj(cnpj).getDoctorList().stream()
                .filter(doctor -> doctor.getSpeciality().equalsIgnoreCase(speciality))
                .collect(Collectors.toList());
    }

    public Doctor register(String token, Doctor doctor) {
        Company company = companyService.getUserByUsername(token);

        try {
            if (company.getDoctorList().stream().noneMatch(dr -> Objects.equals(doctor.getCrm(), dr.getCrm()))) {
                company.getDoctorList().add(doctor);
                companyService.updateCompanyWithToken(token, company);
                return doctor;
            } else {
                throw new RuntimeException("Doctor is already registered");
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @SneakyThrows
    public Doctor updateWithToken(String token, Doctor updateDoctor) {
        Company company = companyService.getUserByUsername(token);
        Doctor doctor = findByCRMWithToken(token, updateDoctor.getCrm());
        var index = company.getDoctorList().indexOf(doctor);

        nullAwareBeanUtilsBean.copyProperties(doctor, updateDoctor);

        company.getDoctorList().set(index, doctor);
        companyService.updateCompany(token, company);
        return doctor;
    }

    @SneakyThrows
    public Doctor update(String cnpj, Doctor updateDoctor) {
        Company company = companyService.getByCnpj(cnpj);
        Doctor doctor = findByCRM(cnpj, updateDoctor.getCrm());
        var index = company.getDoctorList().indexOf(doctor);

        nullAwareBeanUtilsBean.copyProperties(doctor, updateDoctor);

        company.getDoctorList().set(index, doctor);
        companyService.updateCompany(cnpj, company);
        return doctor;
    }

    @SneakyThrows
    public void delete(String token, Integer crm) {
        Company company = companyService.getUserByUsername(token);

        company.getDoctorList().remove(findByCRM(token, crm));

        companyService.updateCompany(token, company);
    }

    public Map<String, Object> avaliableScheduleByCRM(String cnpj, Integer day, Integer month, Integer crm) {
      Doctor doctor = findByCRM(cnpj, crm);

    return avaliableScheduleBySpecialist(cnpj, day, month, doctor.getSpeciality()).stream()
              .filter( schedule -> {
                 var doctorCrm = (Integer) schedule.get("crm");
                 return doctorCrm.equals(doctor.getCrm());
              }).findAny()
              .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public List<Map<String, Object>> avaliableScheduleBySpecialist(String cnpj, Integer day, Integer month, String speciality){
        List<Doctor> doctorList = findAllBySpeciality(cnpj, speciality);

        if (!DateUtils.isValidDate(day, month))
            throw new RuntimeException("Invalid Date");


       return doctorList.stream().map(doctor -> {
            var start = doctor.getStartWork();
            var finish = doctor.getFinishWork();

            var scheduledDateTime = doctor.getScheduledAppointment().stream()
                    .map(Appointment::getStartOfAppointment)
                    .filter(appointmentDateTime ->
                            appointmentDateTime.getDayOfMonth() == day && appointmentDateTime.getMonthValue() == month)
                    .collect(Collectors.toList());

            List<LocalTime> localTimeList = new ArrayList<>();

            while (start.isBefore(finish)) {
                localTimeList.add(start);
                start = start.plusMinutes(30);
            }

            List<LocalDateTime> localDateTimeList = localTimeList.stream()
                    .map(localTime ->
                            LocalDateTime.of(
                                    LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), Month.of(month), day),
                                    localTime))
                    .filter(localDateTime -> scheduledDateTime.stream()
                            .noneMatch(appointmentDateTime -> appointmentDateTime.isEqual(localDateTime))).collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();

            response.put("crm", doctor.getCrm());
            response.put("name", doctor.getName());
            response.put("speciality", doctor.getSpeciality());

            response.put("scheduleAvailable",
                    LocalDate.of(Calendar.getInstance().get(Calendar.YEAR), month, day).isBefore(LocalDate.now())
                            ? Collections.EMPTY_LIST : localDateTimeList);

           return response;
        }).collect(Collectors.toList());
    }
}
