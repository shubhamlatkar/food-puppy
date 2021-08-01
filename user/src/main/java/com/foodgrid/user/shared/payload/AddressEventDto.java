package com.foodgrid.user.shared.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodgrid.common.utility.CrudActions;
import com.foodgrid.user.command.internal.model.aggregate.AddressCommandModel;
import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressEventDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private CrudActions action;
    private String id;
    private String userId;
    private Double x;
    private Double y;
    private String name;
    private String addressLineOne;
    private String addressLineTwo;
    private String pin;
    private String city;
    private String state;
    private Boolean isSelected;

    public AddressEventDto(AddressRequest address, String userId, String id, CrudActions action) {
        this.setPin(address.getPin());
        this.setState(address.getState());
        this.setCity(address.getCity());
        this.setAddressLineOne(address.getAddressLineOne());
        this.setAddressLineTwo(address.getAddressLineTwo());
        this.setX(address.getLocation().getX());
        this.setY(address.getLocation().getY());
        this.setId(id);
        this.setName(address.getName());
        this.setIsSelected(address.getIsSelected());
        this.setUserId(userId);
        this.setAction(action);
    }

    public AddressEventDto(AddressCommandModel address, CrudActions action) {
        this.setPin(address.getAddressDetails().getPin());
        this.setState(address.getAddressDetails().getState());
        this.setCity(address.getAddressDetails().getCity());
        this.setAddressLineOne(address.getAddressDetails().getAddressLineOne());
        this.setAddressLineTwo(address.getAddressDetails().getAddressLineTwo());
        this.setX(address.getLocation().getX());
        this.setY(address.getLocation().getY());
        this.setId(address.getId());
        this.setName(address.getName());
        this.setIsSelected(address.getIsSelected());
        this.setUserId(address.getUserId());
        this.setAction(action);
    }
}
