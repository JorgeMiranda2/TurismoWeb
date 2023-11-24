package com.turismo.Dtos.DtosOutput;

import com.turismo.Models.TouristDestination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTouristDestinationForm {
    private DtoUbications ubications;
    private List<TouristDestination> destinations;
}
