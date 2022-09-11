package br.com.fiap.belive_backend.service;

import br.com.fiap.belive_backend.config.NullAwareBeanUtilsBean;
import br.com.fiap.belive_backend.dto.CustomerDTO;
import br.com.fiap.belive_backend.model.Customer;
import br.com.fiap.belive_backend.model.User;
import br.com.fiap.belive_backend.model.User.UserLogin;
import br.com.fiap.belive_backend.repository.CustomerRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceDefault implements DefaultUserService<Customer, CustomerDTO> {

    private final CustomerRepository customerRepository;

    private NullAwareBeanUtilsBean nullAwareBeanUtilsBean;

    public CustomerServiceDefault(CustomerRepository customerRepository, NullAwareBeanUtilsBean nullAwareBeanUtilsBean){
        this.customerRepository = customerRepository;
        this.nullAwareBeanUtilsBean = nullAwareBeanUtilsBean;
    }

    @Override
    public Customer register(CustomerDTO customerDTO){
        Customer customer = CustomerDTO.toModel(customerDTO);

        encryptPassword(customer.getUserLogin());

        return customerRepository.save(customer);
    }

    @Override
    public Map<String, String> login(UserLogin userLogin) {
        return customerRepository.findByUserLogin_Username(userLogin.getUsername()).stream()
                .filter(user -> user.getTypeOfUser().equals(User.Type.CUSTOMER))
                .findFirst()
                .map(customer -> generateBasicToken(userLogin, customer))
                .orElse(null);
    }

    @Override
    public Customer getUserByUsername(String token) {
        String decodedToken = new String(Base64.getDecoder().decode(token.replace("Basic ", "")));

        List<String> userInfo = Arrays.stream(decodedToken.split(":")).toList();

        return customerRepository.findByUserLogin_Username(userInfo.get(0))
                .filter(user -> user.getTypeOfUser().equals(User.Type.CUSTOMER))
                .orElse(null);
    }

    @SneakyThrows
    public Customer updateCustomer(String token, Customer udpateCustomer) {
        Customer customer = getUserByUsername(token);
        nullAwareBeanUtilsBean.copyProperties(customer, udpateCustomer);
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String token) {
        customerRepository.delete(getUserByUsername(token));
    }
}
