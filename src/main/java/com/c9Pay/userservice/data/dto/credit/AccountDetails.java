package com.c9Pay.userservice.data.dto.credit;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetails {

    String serialNumber;
    Long credit;
}
