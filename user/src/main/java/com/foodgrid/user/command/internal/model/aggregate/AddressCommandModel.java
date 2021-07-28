package com.foodgrid.user.command.internal.model.aggregate;

import com.foodgrid.user.command.internal.payload.dto.request.AddressRequest;
import com.foodgrid.user.shared.model.AddressDetails;
import com.foodgrid.user.shared.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressCommandModel {
    @Id
    private String id;
    private String userId;
    private Location location;
    private String name;
    private AddressDetails addressDetails;
    private Boolean isSelected;

    public AddressCommandModel(String userId, Location location, String name, AddressDetails addressDetails, Boolean isSelected) {
        this.userId = userId;
        this.location = location;
        this.name = name;
        this.addressDetails = addressDetails;
        this.isSelected = isSelected;
    }

    public AddressCommandModel(AddressRequest address, String userId) {
        this.setAddressDetails(new AddressDetails(address.getAddressLineOne(), address.getAddressLineTwo(), address.getPin(), address.getCity(), address.getState()));
        this.setName(address.getName());
        this.setIsSelected(address.getIsSelected());
        this.setLocation(address.getLocation());
        this.setUserId(userId);
    }
}
