package com.foodgrid.restaurant.command.internal.model.entity;

import com.foodgrid.restaurant.shared.utility.DayTimings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimingsCommandModel {
    private DayTimings dayTiming;
    private Integer start;
    private Integer end;
}