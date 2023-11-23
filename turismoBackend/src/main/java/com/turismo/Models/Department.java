package com.turismo.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@Entity
@ToString(exclude="cities")
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @Column(name="name")
    private String name;

    //Relations

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="country_id")
    private Country country;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<City> cities;

    //constructors
    public Department(){}


}
