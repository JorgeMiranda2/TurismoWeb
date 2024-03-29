package com.turismo.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Data
@ToString(exclude="registrations")
@Entity
@Table(name = "user")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name",nullable = false, unique = false, length=40)
    private String name;
    @Column(name="last_name",nullable = false, unique = false, length=40)
    private String lastName;
    @Column(name="user_name",nullable = false, unique = true, length=16)
    private String userName;
    @Column(name="password",nullable = false, length=64)
    private String password;
    @Column(name="email",nullable = false, unique = true, length=50)
    private String email;

    @ManyToOne
    @JoinColumn(name="profile_id")
    private Profile profile;

    @ManyToMany
    @JoinTable(
            name="registrations",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="tourist_plans_id")
    )
    private List<TouristPlan> touristPlans;


    //constructors
    public User(){}

    public User(Long id){
        this.id = id;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((profile.getRole())));
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
