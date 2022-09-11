package br.com.fiap.belive_backend.dto;

import br.com.fiap.belive_backend.model.User.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @Pattern(regexp = "^[a-zA-Z0-9.-]+@[a-zA-Z-]+\\.(com|edu|net)(\\.[a-z]{2})?$", message = "Enter a valid email")
    private String username;

    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "password must contain at least eight characters, " +
                    "at least one number and both lower and uppercase letters and special characters")
    private String password;

    public static UserLogin toModel(UserLoginDTO userLoginDTO){
        return UserLogin.builder()
                .username(userLoginDTO.getUsername())
                .password(userLoginDTO.getPassword())
                .build();
    }
}
