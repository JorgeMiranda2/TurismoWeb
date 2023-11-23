package com.turismo.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString(exclude="users")
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name", nullable = false,unique = false, length=16)
    private String name;
    @Column(name="role", nullable = false,unique = false, length=16)
    private String role;


    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    private List<User> users;


    //constructors
    public Profile(){}
}
