package com.turismo.Dtos.DtosInput;

import com.turismo.Models.Enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoTouristPlanRegister {
    private String name;
    private List<Long> touristDestinationIds;
    private BigDecimal price;

    private int days;
    private int nights;

    private TransportType transportType;

    private int numEnabledPackages;
}
