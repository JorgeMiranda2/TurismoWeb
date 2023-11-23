package com.turismo.services;

import com.turismo.Models.Department;
import com.turismo.Repositories.IDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final IDepartment departmentRepo;

    @Autowired
    public DepartmentService(IDepartment departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

   public List<Department> getDepartmentsByCountry(Long id){
        return departmentRepo.getDepartmentsByCountry(id);
    }
    
    public List<Department> list(){
        return departmentRepo.findAll();
    }

    public Optional<Department> getDepartamentById(Long id){
        return departmentRepo.findById(id);
    }

    public Long save(Department departament){
        Department savedDepartament = departmentRepo.save(departament);
        return savedDepartament.getId();
    }

    public void delete(Department departament){
        departmentRepo.delete(departament);
    }
}
