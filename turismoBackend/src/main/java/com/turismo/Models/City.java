package com.turismo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString(exclude="lodgings")
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @Column(name="name")
    private String name;

    //Relations

    @ManyToOne
    @JoinColumn(name="department_id")
    @JsonIgnore
    private Department department;

    @OneToMany(mappedBy = "city")
    private List<Lodging> lodgings;


    public City(){}

public City(Long id){
    this.id = id;
}

}
