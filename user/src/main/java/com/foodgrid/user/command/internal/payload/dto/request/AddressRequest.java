package com.foodgrid.user.command.internal.payload.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodgrid.user.shared.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressRequest {
    private Location location;
    private String name;
    private String addressLineOne;
    private String addressLineTwo;
    @NotNull
    @Size(max = 6, min = 6)
    private String pin;
    private String city;
    private String state;
    private Boolean isSelected;
}
