package com.turismo.Dtos.DtosOutput;

import com.turismo.Models.Enums.LodgingType;
import com.turismo.Models.Enums.TransportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class DtoLodgingTypes {
    private List<String> lodgingTypes;
    public DtoLodgingTypes() {
        this.lodgingTypes = Arrays.stream(LodgingType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
