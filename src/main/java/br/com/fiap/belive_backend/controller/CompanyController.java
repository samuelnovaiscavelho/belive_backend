package br.com.fiap.belive_backend.controller;

import br.com.fiap.belive_backend.dto.CompanyDTO;
import br.com.fiap.belive_backend.model.Company;
import br.com.fiap.belive_backend.model.Customer;
import br.com.fiap.belive_backend.model.User;
import br.com.fiap.belive_backend.service.CompanyServiceDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user/company")
public class CompanyController {

    private final CompanyServiceDefault companyService;

    public CompanyController(CompanyServiceDefault companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@Valid @RequestBody CompanyDTO companyDTO){
        return new ResponseEntity(companyService.register(companyDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody User.UserLogin userLogin){
        Map<String, String> message = companyService.login(userLogin);

        if(Objects.isNull(message))
            throw new UsernameNotFoundException("Username or password incorrect");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Company> getCustomer(@RequestHeader(value = "Authorization") String token){
        return ResponseEntity.ok(companyService.getUserByUsername(token));
    }

    @PatchMapping("/update")
    public ResponseEntity<Company> updateCompany(@RequestHeader(value = "Authorization") String token, @RequestBody Company company ){
        return ResponseEntity.ok(companyService.updateCompanyWithToken(token, company));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(value = "Authorization") String token) {
        companyService.deleteCompany(token);
        return ResponseEntity.ok().build();
    }
}
