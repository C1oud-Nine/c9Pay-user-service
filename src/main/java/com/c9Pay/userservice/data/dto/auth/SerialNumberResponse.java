package com.c9Pay.userservice.data.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SerialNumberResponse {
    private UUID serialNumber;
}
