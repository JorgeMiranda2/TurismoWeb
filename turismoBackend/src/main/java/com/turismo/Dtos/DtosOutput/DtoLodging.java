package com.turismo.Dtos.DtosOutput;

import com.turismo.Models.Lodging;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoLodging {
    private Lodging lodging;
    private DtoCity city;
}
