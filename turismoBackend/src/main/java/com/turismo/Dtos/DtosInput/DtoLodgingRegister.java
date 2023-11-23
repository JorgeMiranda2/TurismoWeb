package com.turismo.Dtos.DtosInput;

import com.turismo.Models.City;
import com.turismo.Models.Enums.LodgingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoLodgingRegister {


    private String name;

    private LodgingType lodgingType;

    private int numRooms;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private Long cityId;

}
