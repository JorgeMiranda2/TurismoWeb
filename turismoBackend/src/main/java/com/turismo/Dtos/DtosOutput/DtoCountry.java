package com.turismo.Dtos.DtosOutput;

import com.turismo.Models.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoCountry {
    private long id;
    private String name;

public DtoCountry(Country country){
    this.name = country.getName();
    this.id = country.getId();

}
}
