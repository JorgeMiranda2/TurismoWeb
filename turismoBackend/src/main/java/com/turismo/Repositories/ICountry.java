package com.turismo.Repositories;


import com.turismo.Models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountry extends JpaRepository<Country,Long> {
}
