package com.turismo.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString(exclude="departments")
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<Department> departments;


    //constructor

    public Country(){}
}
