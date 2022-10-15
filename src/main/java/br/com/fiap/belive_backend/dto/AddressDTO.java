package br.com.fiap.belive_backend.dto;

import br.com.fiap.belive_backend.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @Size(min = 4, max = 100, message = "Street must contain {min} to {max} characters")
    @NotNull(message = "Street cannot be null")
    @NotEmpty(message = "Street cannot be empty")
    private String street;

    @Size(min = 4, max = 50, message = "District must contain {min} to {max} characters")
    @NotNull(message = "District cannot be null")
    @NotEmpty(message = "District cannot be empty")
    private String district;

    @Size(min = 4, max = 50, message = "City must contain {min} to {max} characters")
    @NotNull(message = "City cannot be null")
    @NotEmpty(message = "City cannot be empty")
    private String city;

    @Size(min = 4, max = 20, message = "State must contain {min} to {max} characters")
    @NotNull(message = "State cannot be null")
    @NotEmpty(message = "State cannot be empty")
    private String state;

    @Pattern(regexp = "^([0-9]{5})-([0-9]{3})$", message = "zip code must follow the pattern [00000-000]" )
    @NotNull(message = "zipCode cannot be null")
    @NotEmpty(message = "zipCode cannot be empty")
    private String zipCode;

    public static Address toModel(AddressDTO addressDTO) {
        return Address.builder()
                .street(addressDTO.getStreet())
                .district(addressDTO.getDistrict())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .build();
    }

    public static AddressDTO toDTO(Address address) {
        return AddressDTO.builder()
                .street(address.getStreet())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }
}
