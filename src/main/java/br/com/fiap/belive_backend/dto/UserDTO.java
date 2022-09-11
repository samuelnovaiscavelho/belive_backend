package br.com.fiap.belive_backend.dto;

import br.com.fiap.belive_backend.model.User.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@SuperBuilder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @JsonIgnore
    private String id;

    @Size(min = 5, max = 255, message = "Name must contain {min} to {max} characters")
    private String name;

    @JsonProperty("address")
    @Valid
    private AddressDTO addressDTO;

    @JsonProperty("userLogin")
    @Valid
    private UserLoginDTO userLoginDTO;

    private Type type;
}
