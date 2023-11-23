package com.turismo.Models;


import com.turismo.Dtos.DtosInput.DtoLodgingRegister;
import com.turismo.Models.Enums.LodgingType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name="lodging")
public class Lodging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="lodging_type")
    @Enumerated(EnumType.ORDINAL)
    private LodgingType lodgingType;

    @Column(name="num_rooms")
    private int numRooms;

    @Column(name="check_in")
    private LocalTime checkIn;

    @Column(name="check_out")
    private LocalTime checkOut;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;


    //constructors
    public Lodging(){}
    public Lodging(DtoLodgingRegister dtoLodgingRegister){
        this.name = dtoLodgingRegister.getName();
        this.checkIn = dtoLodgingRegister.getCheckIn();
        this.checkOut = dtoLodgingRegister.getCheckOut();
        this.city = new City(dtoLodgingRegister.getCityId());
        this.lodgingType = dtoLodgingRegister.getLodgingType();
        // Verifica si el tipo de hospedaje es "houseShared"
        if (this.lodgingType == LodgingType.Shared_house) {
            this.numRooms = 0; // Si es "houseShared", establecer numRooms a 0
        } else {
            this.numRooms = dtoLodgingRegister.getNumRooms(); // establecer el número de habitaciones desde el DTO
        }
    }

}
