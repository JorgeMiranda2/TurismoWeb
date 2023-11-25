package com.turismo.Dtos.DtosOutput;

import com.turismo.Models.Enums.LodgingType;
import com.turismo.Models.Enums.TransportType;
import com.turismo.Models.TouristDestination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTouristPlanQuota {
    private Long id;
    private String name;
    private int availableQuotas;
    private BigDecimal price;
    private int days;
    private int nights;
    private TransportType transportType;
    private List<TouristDestination> touristDestinations;

}
