package com.turismo.Dtos.DtosOutput;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.turismo.Models.City;
import com.turismo.Models.TouristDestination;
import com.turismo.Models.TouristPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DtoTouristDestination {
    private Long id;
    private String name;
    private DtoCity city;
}


