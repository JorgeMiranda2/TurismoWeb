package com.turismo.Repositories;


import com.turismo.Models.TouristDestination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITouristDestination extends JpaRepository<TouristDestination,Long> {
}
