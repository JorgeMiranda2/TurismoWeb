package com.turismo.Repositories;

import com.turismo.Models.City;
import com.turismo.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICity extends JpaRepository<City,Long> {
    @Query(value = "SELECT * FROM city WHERE department_id = ?1",nativeQuery = true)
    List<City> getCitiesByDepartment(Long id);
}
