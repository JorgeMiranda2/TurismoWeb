package com.turismo.Dtos.DtosOutput;

import com.turismo.Models.City;
import com.turismo.Models.Country;
import com.turismo.Models.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoUbications {
    private List<DtoCountry> countries;
    private List<DtoDepartment> departments;
    private List<DtoCity> cities;
}
