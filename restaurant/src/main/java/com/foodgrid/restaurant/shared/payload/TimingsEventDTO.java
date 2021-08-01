package com.foodgrid.restaurant.shared.payload;

import com.foodgrid.restaurant.shared.utility.DayTimings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimingsEventDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private DayTimings dayTiming;
    private Integer start;
    private Integer end;
}
