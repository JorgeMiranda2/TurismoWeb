package com.turismo.Repositories;

import com.turismo.Models.Lodging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILodging extends JpaRepository<Lodging,Long> {
}
