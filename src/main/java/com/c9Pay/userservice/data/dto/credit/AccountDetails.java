package com.c9Pay.userservice.data.dto.credit;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {

    @NotBlank
    String serialNumber;
    @Min(value = 0)
    Long credit;
}
