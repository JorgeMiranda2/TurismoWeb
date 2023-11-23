package com.turismo.Repositories;

import com.turismo.Models.TouristPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITouristPlan extends JpaRepository<TouristPlan,Long> {
}
