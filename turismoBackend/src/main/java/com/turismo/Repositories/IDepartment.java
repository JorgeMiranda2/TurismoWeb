package com.turismo.Repositories;

import com.turismo.Models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepartment extends JpaRepository<Department,Long> {
    @Query(value = "SELECT * FROM department WHERE country_id = ?1",nativeQuery = true)
    List<Department> getDepartmentsByCountry(Long id);
}
