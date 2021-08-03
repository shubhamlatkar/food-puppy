package com.foodgrid.user.command.internal.payload.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequest {
    @NotNull
    private String restaurantId;
    @NotNull
    private String itemId;
}
