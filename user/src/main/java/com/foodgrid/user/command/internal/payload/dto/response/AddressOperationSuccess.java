package com.foodgrid.user.command.internal.payload.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressOperationSuccess {
    private String id;
    private String message;
}
