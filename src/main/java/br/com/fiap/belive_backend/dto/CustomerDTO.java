package br.com.fiap.belive_backend.dto;

import br.com.fiap.belive_backend.model.Customer;
import br.com.fiap.belive_backend.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
public class CustomerDTO extends UserDTO {

    @CPF(message = "CPF invalid")
    @Valid
    private String cpf;

    public static Customer toModel(CustomerDTO customerDTO) {
        return Customer.builder()
                .name(customerDTO.getName())
                .userLogin(UserLoginDTO.toModel(customerDTO.getUserLoginDTO()))
                .address(AddressDTO.toModel(customerDTO.getAddressDTO()))
                .cpf(customerDTO.getCpf())
                .typeOfUser(User.Type.CUSTOMER)
                .build();
    }
}
