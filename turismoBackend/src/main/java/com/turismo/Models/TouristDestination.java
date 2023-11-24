package com.turismo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString(exclude="touristPlans")
@Table(name="tourist_destination")
public class TouristDestination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    @ManyToMany(mappedBy = "touristDestinations")
    @JsonBackReference
    private List<TouristPlan> touristPlans;


    //Constructors

    public TouristDestination(){}
    public TouristDestination(String name, Long id){
        this.name = name;
        this.city = new City(id);
    }
    public TouristDestination(Long id){
        this.id = id;
    }
}
