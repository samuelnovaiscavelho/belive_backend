package br.com.fiap.belive_backend.service;

import br.com.fiap.belive_backend.config.NullAwareBeanUtilsBean;
import br.com.fiap.belive_backend.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private DoctorRepository doctorRepository;

    private NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    private DoctorService(DoctorRepository doctorRepository){
        this.doctorRepository = doctorRepository;
    }
}
