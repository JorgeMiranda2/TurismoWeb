package com.turismo.services;

import com.turismo.Models.Country;
import com.turismo.Repositories.ICountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final ICountry countryRepo;

    @Autowired
    public CountryService(ICountry countryRepo) {
        this.countryRepo = countryRepo;
    }

    public List<Country> list(){
        return countryRepo.findAll();
    }



    public Optional<Country> getCountryById(Long id){
        return countryRepo.findById(id);
    }

    public Long save(Country country){
        Country savedCountry = countryRepo.save(country);
        return savedCountry.getId();
    }

    public void delete(Country country){
        countryRepo.delete(country);
    }
}
