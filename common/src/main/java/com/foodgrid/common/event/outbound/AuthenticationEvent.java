package com.foodgrid.common.event.outbound;

import com.foodgrid.common.payload.dto.event.UserAuthEventDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticationEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean isUpdated;
    private List<UserAuthEventDTO> users;
}
