package com.turismo.services;

import com.turismo.Models.City;
import com.turismo.Models.Department;
import com.turismo.Repositories.ICity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class CityService {


    private final ICity cityRepo;

    @Autowired
    public CityService(ICity cityRepo) {
        this.cityRepo = cityRepo;
    }


    public List<City> list(){
        List<City> city;
        city = cityRepo.findAll();
        return city;
    }

    public Optional<City> getLodgingId(Long id){
        return cityRepo.findById(id);
    }

    public List<City> getCitiesByDepartment(Long id){
        return cityRepo.getCitiesByDepartment(id);
    }

    public Long save(City city){
        City savedCity = cityRepo.save(city);
        return savedCity.getId();
    }

    public void delete(City city){
        cityRepo.delete(city);
    }


}
