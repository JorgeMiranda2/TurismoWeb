package com.turismo.Controllers;

import com.turismo.Dtos.DtosOutput.DtoCity;
import com.turismo.Dtos.DtosOutput.DtoCountry;
import com.turismo.Dtos.DtosOutput.DtoDepartment;
import com.turismo.Dtos.DtosOutput.DtoUbications;
import com.turismo.Models.City;
import com.turismo.Models.Country;
import com.turismo.Models.Department;
import com.turismo.services.CityService;
import com.turismo.services.CountryService;
import com.turismo.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UbicationController {

    private final CountryService countryService;
    private final DepartmentService departmentService;
    private final CityService cityService;

    @Autowired
    public UbicationController(CountryService countryService,
                              DepartmentService departmentService,
                              CityService cityService) {
        this.countryService = countryService;
        this.departmentService = departmentService;
        this.cityService = cityService;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.list();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/departments/{countryId}")
    public ResponseEntity<List<Department>> getDepartmentsByCountry(@PathVariable Long countryId) {
        List<Department> departments = departmentService.getDepartmentsByCountry(countryId);
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping("/cities/{departmentId}")
    public ResponseEntity<List<City>> getCitiesByDepartment(@PathVariable Long departmentId) {
        List<City> cities = cityService.getCitiesByDepartment(departmentId);
        return ResponseEntity.ok().body(cities);
    }

    @GetMapping("/ubications")
    public ResponseEntity<DtoUbications> getUbications() {

        List<DtoCity> cities = cityService.list().stream().map((specificCity)->{
            return new DtoCity(specificCity);
        }).toList();
        List<DtoDepartment> departments = departmentService.list().stream()
                .map(DtoDepartment::new)
                .toList();

        List<DtoCountry> countries = countryService.list().stream()
                .map(DtoCountry::new)
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(DtoUbications.builder()
                .cities(cities)
                .departments(departments)
                .countries(countries)
                .build());
    }
}
