package com.turismo.Controllers;

import com.turismo.Dtos.DtosOutput.*;
import com.turismo.Models.City;
import com.turismo.Models.Country;
import com.turismo.Models.Department;
import com.turismo.Models.Enums.LodgingType;
import com.turismo.services.CityService;
import com.turismo.services.CountryService;
import com.turismo.services.DepartmentService;
import com.turismo.services.TouristDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class InfoController {

    private final CountryService countryService;
    private final DepartmentService departmentService;
    private final CityService cityService;
    private final TouristDestinationService touristDestinationService;

    @Autowired
    public InfoController(CountryService countryService,
                          DepartmentService departmentService,
                          CityService cityService, TouristDestinationService touristDestinationService) {
        this.countryService = countryService;
        this.departmentService = departmentService;
        this.cityService = cityService;
        this.touristDestinationService = touristDestinationService;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.list();
        return ResponseEntity.ok().body(countries);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities() {
        List<City> cities = cityService.list();
        return ResponseEntity.ok().body(cities);
    }

    @GetMapping("/touristplansbasics")
    public ResponseEntity<DtoTouristPlanForm> getTouristPlanFormInfo() {
        List<DtoTouristDestination> touristDestinations = touristDestinationService.list().stream().map(
                (touristDestination -> {
                    return DtoTouristDestination.builder()
                            .name(touristDestination.getName())
                            .id(touristDestination.getId())
                            .city(DtoCity.builder()
                                    .name(touristDestination.getCity().getName())
                                    .id(touristDestination.getCity().getId())
                                    .departmentId(touristDestination.getCity().getDepartment().getId())
                                    .build()
                            ).build();

                })
        ).collect(Collectors.toList());

        DtoTouristPlanForm dtoTouristPlanForm = new DtoTouristPlanForm();
        dtoTouristPlanForm.setDestinations(touristDestinations);

        return ResponseEntity.ok().body(dtoTouristPlanForm);
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
    @GetMapping("/lodgingtypes")
    public ResponseEntity<DtoLodgingTypes> getLodgingTypes() {
        return ResponseEntity.ok().body(new DtoLodgingTypes());
    }

}
