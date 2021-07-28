package com.foodgrid.user.query.internal.model.aggregate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.foodgrid.user.shared.model.AddressDetails;
import com.foodgrid.user.shared.model.Location;
import com.foodgrid.user.shared.payload.AddressEventDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressQueryModel {
    @Id
    private String id;
    private String userId;
    private Location location;
    private String name;
    private AddressDetails addressDetails;
    private Boolean isSelected;

    public AddressQueryModel(AddressEventDto address) {
        this.id = address.getId();
        this.location = new Location(address.getX(), address.getY());
        this.addressDetails = new AddressDetails(address.getAddressLineOne(), address.getAddressLineTwo(), address.getPin(), address.getCity(), address.getState());
        this.name = address.getName();
        this.isSelected = address.getIsSelected();
        this.userId = address.getUserId();
    }
}
