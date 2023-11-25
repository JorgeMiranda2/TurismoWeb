package com.turismo.Dtos.DtosOutput;


import com.turismo.Models.Enums.TransportType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Data
@Builder
@AllArgsConstructor
public class DtoTouristPlanForm {
    private List<String> transportTypes;
    private List<DtoTouristDestination> destinations;
public DtoTouristPlanForm() {
    this.transportTypes = Arrays.stream(TransportType.values())
            .map(Enum::name)
            .collect(Collectors.toList());
}
}
