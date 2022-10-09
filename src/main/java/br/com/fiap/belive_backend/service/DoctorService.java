package br.com.fiap.belive_backend.service;

import br.com.fiap.belive_backend.config.NullAwareBeanUtilsBean;
import br.com.fiap.belive_backend.exception.UserNotFoundException;
import br.com.fiap.belive_backend.model.Company;
import br.com.fiap.belive_backend.model.Doctor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private CompanyServiceDefault companyService;

    private NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    private DoctorService(CompanyServiceDefault companyService, NullAwareBeanUtilsBean nullAwareBeanUtilsBean){
        this.companyService = companyService;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
    }

    public List<Doctor> findAll(String token){
        return companyService.getUserByUsername(token).getDoctorList();
    }

    public Doctor findByCRM(String token, Integer crm){
        return findAll(token).stream()
                .filter(doctor -> Objects.equals(doctor.getCrm(), crm))
                .findAny().orElseThrow(() -> new UserNotFoundException("Doctor not found"));
    }

    public List<Doctor> findAllBySpeciality(String token, String speciality){
        return findAll(token).stream()
                .filter(doctor -> doctor.getSpeciality().equalsIgnoreCase(speciality))
                .collect(Collectors.toList());
    }

    public Doctor register(String token, Doctor doctor){
        Company company = companyService.getUserByUsername(token);
        try{
            if(company.getDoctorList().stream().noneMatch(dr -> Objects.equals(doctor.getCrm(), dr.getCrm()))) {
                company.getDoctorList().add(doctor);
                companyService.updateCompany(token, company);
                return doctor;
            }else{
                throw new RuntimeException("Doctor is already registered");
            }
        }catch (Exception exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    @SneakyThrows
    public Doctor update(String token, Doctor updateDoctor) {
        Company company = companyService.getUserByUsername(token);
        Doctor doctor = findByCRM(token, updateDoctor.getCrm());
        var index = company.getDoctorList().indexOf(doctor);

        nullAwareBeanUtilsBean.copyProperties(doctor, updateDoctor);

        company.getDoctorList().set(index, doctor);
        companyService.updateCompany(token, company);
        return doctor;
    }

    @SneakyThrows
    public void delete(String token, Integer crm){
        Company company = companyService.getUserByUsername(token);

        company.getDoctorList().remove(findByCRM(token, crm));

        companyService.updateCompany(token, company);
    }
}
