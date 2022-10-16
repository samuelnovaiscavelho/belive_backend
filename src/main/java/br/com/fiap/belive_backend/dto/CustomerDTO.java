package br.com.fiap.belive_backend.dto;

import br.com.fiap.belive_backend.model.Customer;
import br.com.fiap.belive_backend.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
public class CustomerDTO extends UserDTO {

    @NotNull(message = "CNPJ cant'be null")
    @CPF(message = "CPF invalid")
    @Valid
    private String cpf;

    public static Customer toModel(CustomerDTO customerDTO) {
        return Customer.builder()
                .name(customerDTO.getName())
                .userLogin(UserLoginDTO.toModel(customerDTO.getUserLoginDTO()))
                .cpf(customerDTO.getCpf())
                .phone(customerDTO.getPhone())
                .typeOfUser(User.Type.CUSTOMER)
                .build();
    }

    public static CustomerDTO toDTO(Customer customer) {
        return CustomerDTO.builder()
                .name(customer.getName())
                .userLoginDTO(UserLoginDTO.toDTO(customer.getUserLogin()))
                .cpf(customer.getCpf())
                .phone(customer.getPhone())
                .typeOfUser(customer.getTypeOfUser())
                .build();
    }
}
