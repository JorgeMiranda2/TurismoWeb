package com.turismo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.turismo.Dtos.DtosInput.DtoTouristPlanRegister;
import com.turismo.Models.Enums.TransportType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@ToString(exclude="users")
@Table(name="tourist_plans")
public class TouristPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="price",precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name="num_days")
    private int days;

    @Column(name="num_nights")
    private int nights;

    @Column(name="transport_type")
    @Enumerated(EnumType.ORDINAL)
    private TransportType transportType;

    @Column(name="num_enabled_packages")
    private int numEnabledPackages;

    @ManyToMany(mappedBy = "touristPlans")
    @JsonBackReference
    private List<User> users;

    @ManyToMany
    @JoinTable(
            name="plans_destination",
            joinColumns = @JoinColumn(name="tourist_plans_id"),
            inverseJoinColumns = @JoinColumn(name="tourist_destination_id")
    )
    private List<TouristDestination> touristDestinations;


    //constructors

    public TouristPlan(){}
    public TouristPlan(DtoTouristPlanRegister dtoTouristPlanRegister){
        this.name = dtoTouristPlanRegister.getName();
        this.days = dtoTouristPlanRegister.getDays();
        this.nights = dtoTouristPlanRegister.getNights();
        this.numEnabledPackages = dtoTouristPlanRegister.getNumEnabledPackages();
        this.transportType = dtoTouristPlanRegister.getTransportType();
        this.price = dtoTouristPlanRegister.getPrice();
        this.touristDestinations = dtoTouristPlanRegister.getTouristDestinationIds().stream().map((touristDestinationid)->{
            return new TouristDestination(touristDestinationid);
                }
        ).collect(Collectors.toList());

    }
}
