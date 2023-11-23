package com.turismo.Dtos.DtosOutput;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTouristPlanQuota {
    private Long id;
    private String name;
    private int availableQuotas;

}