package br.com.fiap.belive_backend.controller;

import br.com.fiap.belive_backend.dto.CustomerDTO;
import br.com.fiap.belive_backend.model.Customer;
import br.com.fiap.belive_backend.model.User.UserLogin;
import br.com.fiap.belive_backend.service.CustomerServiceDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user/customer")
public class CustomerController {

    private final CustomerServiceDefault customerService;

    public CustomerController(CustomerServiceDefault customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@Valid @RequestBody CustomerDTO customerDTO){
        if (customerDTO.getUserLoginDTO().getPassword().equals(null)){
            throw new RuntimeException("Senha nula");
        }
        return new ResponseEntity<>(customerService.register(customerDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLogin userLogin){
        Map<String, String> message = customerService.login(userLogin);

        if(Objects.isNull(message))
            throw new UsernameNotFoundException("Username or password incorrect");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Customer> getCustomer(@RequestHeader(value = "Authorization") String token){
        return ResponseEntity.ok(customerService.getUserByUsername(token));
    }

    @PatchMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestHeader(value = "Authorization") String token, @RequestBody Customer customer){
        return ResponseEntity.ok(customerService.updateCustomer(token, customer));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestHeader(value = "Authorization") String token){
        customerService.deleteCustomer(token);
        return ResponseEntity.ok().build();
    }
}
