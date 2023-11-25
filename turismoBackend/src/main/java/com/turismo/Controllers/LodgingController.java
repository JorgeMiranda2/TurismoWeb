package com.turismo.Controllers;


import com.turismo.Dtos.DtosInput.DtoLodgingRegister;
import com.turismo.Dtos.DtosOutput.DtoCity;
import com.turismo.Dtos.DtosOutput.DtoLodging;
import com.turismo.Models.City;
import com.turismo.Models.Lodging;
import com.turismo.Models.TouristDestination;
import com.turismo.services.LodgingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api")
public class LodgingController {

    @Autowired
    private  LodgingService lodgingService;

    public LodgingController(LodgingService lodgingService) {
        this.lodgingService = lodgingService;
    }

    @PostMapping("/lodging")
    public ResponseEntity<String> create(@RequestBody Lodging lodging){
        System.out.println("Getting a post request to lodging");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        System.out.println(lodging);
        //Saving the lodging by service
        Long lodgingId = lodgingService.save(lodging);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(lodgingId).toUri();
        //returning the response
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Touristdestination created in: " + location);
    }

    @PostMapping("/lodgingadmin")
    public ResponseEntity<Map<String,String>> create(@RequestBody DtoLodgingRegister dtoLodgingRegister){
        System.out.println("Getting a post request to dto lodging");

        Lodging lodging = new Lodging(dtoLodgingRegister);
        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        System.out.println(lodging);
        //Saving the lodging by service
        Long lodgingId = lodgingService.save(lodging);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(lodgingId).toUri();
        //returning the response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Lodging created");
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    @PutMapping("/lodging/{id}")
    public ResponseEntity<Map<String,String>> updateLodging(@PathVariable Long id , @RequestBody DtoLodgingRegister dtoLodgingRegister){
        System.out.println("Getting a post request to dto lodging");

        Optional<Lodging> existingLodging = lodgingService.getLodgingId(id);

        if (existingLodging.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Lodging lodging = existingLodging.get();

        lodging.setName(dtoLodgingRegister.getName());
        lodging.setCity(new City(dtoLodgingRegister.getCityId()));
        lodging.setLodgingType(dtoLodgingRegister.getLodgingType());
        lodging.setCheckIn(dtoLodgingRegister.getCheckIn());
        lodging.setCheckOut(dtoLodgingRegister.getCheckOut());
        lodging.setNumRooms(dtoLodgingRegister.getNumRooms());
        // Actualiza otros campos según sea necesario

        lodgingService.save(lodging);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Lodging updated");
        return ResponseEntity.ok(response);

        //returning the response
    }
    @GetMapping("/lodging")
    public ResponseEntity<Collection<DtoLodging>> listlodgings(){
        System.out.println("Getting a get request to lodging");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all lodgings by service
        Collection<Lodging> lodgings = lodgingService.list();
        Collection<DtoLodging> dtoLodgings = lodgings.stream().map(
                (lodging)->{
                    return DtoLodging.builder().lodging(lodging).city(
                            DtoCity.builder().id(lodging.getCity().getId())
                                    .name(lodging.getCity().getName())
                                    .departmentId(lodging.getCity().getDepartment().getId())
                                    .build()
                    ).build();
                }
        ).collect(Collectors.toList());
        System.out.println(lodgings);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(dtoLodgings);
    }

    @DeleteMapping("/lodging/{id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable Long id){
        Optional<Lodging> isLoding = lodgingService.getLodgingId(id);
        if(isLoding.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        lodgingService.delete(isLoding.get());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Lodging deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
