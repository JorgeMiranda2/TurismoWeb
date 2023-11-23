package com.turismo.Dtos.DtosOutput;

import com.turismo.Models.City;
import com.turismo.Models.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoDepartment {
    private long id;
    private String name;
    private Long CountryId;


    public DtoDepartment(Department department){
        this.id = department.getId();
        this.name = department.getName();
        this.CountryId = department.getCountry().getId();

    }
}
