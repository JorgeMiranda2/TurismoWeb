package com.turismo.Dtos.DtosOutput;

import com.turismo.Models.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoCity {
    private long id;
    private String name;
    private Long departmentId;
    

    public DtoCity(City city){
        this.id = city.getId();
        this.name = city.getName();
        this.departmentId = city.getDepartment().getId();

    }
}
