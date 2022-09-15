package br.com.fiap.belive_backend.dto;

import br.com.fiap.belive_backend.model.Company;
import br.com.fiap.belive_backend.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
public class CompanyDTO extends UserDTO{

    @NotNull(message = "CNPJ cant'be null")
    @CNPJ(message = "Invalid CNPJ")
    @Valid
    private String cnpj;

    public static Company toModel(CompanyDTO companyDTO) {
        return Company.builder()
                .name(companyDTO.getName())
                .userLogin(UserLoginDTO.toModel(companyDTO.getUserLoginDTO()))
                .address(AddressDTO.toModel(companyDTO.getAddressDTO()))
                .cnpj(companyDTO.getCnpj())
                .typeOfUser(User.Type.COMPANY)
                .build();
    }
}
