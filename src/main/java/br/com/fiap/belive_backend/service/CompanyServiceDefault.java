package br.com.fiap.belive_backend.service;

import br.com.fiap.belive_backend.config.NullAwareBeanUtilsBean;
import br.com.fiap.belive_backend.dto.CompanyDTO;
import br.com.fiap.belive_backend.exception.UserNotFoundException;
import br.com.fiap.belive_backend.model.Company;
import br.com.fiap.belive_backend.model.User;
import br.com.fiap.belive_backend.model.User.UserLogin;
import br.com.fiap.belive_backend.repository.CompanyRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyServiceDefault implements DefaultUserService<Company, CompanyDTO> {

    private final CompanyRepository companyRepository;

    private NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    public CompanyServiceDefault(CompanyRepository companyRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean){
        this.companyRepository = companyRepository;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
    }

    @Override
    public Company register(CompanyDTO companyDTO) {
        Company company = CompanyDTO.toModel(companyDTO);

        encryptPassword(company.getUserLogin());

        return companyRepository.save(company);
    }

    @Override
    public Map<String, String> login(UserLogin userLogin) {
        return companyRepository.findByUserLogin_Username(userLogin.getUsername()).stream()
                .filter(user -> user.getTypeOfUser().equals(User.Type.COMPANY))
                .findFirst()
                .map(company -> generateBasicToken(userLogin, company))
                .orElse(null);
    }

    @Override
    public Company getUserByUsername(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token.replace("Basic ", "")));

        String username = Arrays.stream(decodedToken.split(":")).toList().get(0);

        return companyRepository.findByUserLogin_Username(username)
                .filter(user -> user.getTypeOfUser().equals(User.Type.COMPANY))
                .orElseThrow(() -> new UserNotFoundException("Company not found"));
    }

    @SneakyThrows
    public Company updateCompany(String token, Company updateCompany){
        Company company = getUserByUsername(token);
        nullAwareBeanUtilsBean.copyProperties(company, updateCompany);
        return companyRepository.save(company);
    }

    public void deleteCompany(String token){
        companyRepository.delete(getUserByUsername(token));
    }
}
