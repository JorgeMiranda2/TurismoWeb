package com.turismo.Controllers;


import com.turismo.Dtos.DtosInput.DtoTouristDestinationRegister;
import com.turismo.Dtos.DtosInput.DtoTouristPlanRegister;
import com.turismo.Dtos.DtosOutput.DtoCity;
import com.turismo.Dtos.DtosOutput.DtoTouristDestination;
import com.turismo.Models.City;
import com.turismo.Models.TouristDestination;
import com.turismo.services.TouristDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api")
public class TouristDestinationController {


    private  final TouristDestinationService touristDestinationService;
    @Autowired
    public TouristDestinationController(TouristDestinationService touristDestinationService) {
        this.touristDestinationService = touristDestinationService;
    }

    @PostMapping("/touristdestination")
    public ResponseEntity<String> create(@RequestBody TouristDestination touristDestination){
        System.out.println("Getting a post request to touristDestination");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        System.out.println(touristDestination);
        //Saving the touristDestination by service
        Long touristDestinationId = touristDestinationService.save(touristDestination);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(touristDestinationId).toUri();
        //returning the response
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Touristdestination created in: " + location);
    }

    @PostMapping("/touristdestinationadmin")
    public ResponseEntity<Map<String, String>> createTouristDestination(@Valid @RequestBody DtoTouristDestinationRegister dtoTouristDestinationRegister){
        System.out.println("Getting a post request to dto touristDestination");

        TouristDestination touristDestination =  new TouristDestination(dtoTouristDestinationRegister.getName(), dtoTouristDestinationRegister.getCityId());

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        System.out.println(touristDestination);
        //Saving the touristDestination by service

            touristDestinationService.save(touristDestination);

        //returning the response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Touristdestination created");

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
    }

    @GetMapping("/touristdestination")
    public ResponseEntity<Collection<DtoTouristDestination>> listTouristDestinations(){
        System.out.println("Getting a get request to touristDestination");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all touristDestinations by service
        Collection<DtoTouristDestination> touristDestinations = touristDestinationService.list().stream().map(
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


        System.out.println(touristDestinations);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(touristDestinations);
    }

    @DeleteMapping("/touristdestination/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id){
        Optional<TouristDestination> isTouristDestination = touristDestinationService.getTouristDestinationId(id);
        if(isTouristDestination.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        touristDestinationService.delete(isTouristDestination.get());
        Map<String, String> response = new HashMap<>();
        response.put("message", "TouristDestination deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @PutMapping("/touristdestination/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable Long id, @Valid @RequestBody DtoTouristDestinationRegister dtoTouristDestinationRegister) {
        Optional<TouristDestination> existingTouristDestination = touristDestinationService.getTouristDestinationId(id);

        if (existingTouristDestination.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TouristDestination touristDestinationToUpdate = existingTouristDestination.get();
        // Actualiza los campos del destino turístico con los datos proporcionados en dtoTouristDestinationRegister
        touristDestinationToUpdate.setName(dtoTouristDestinationRegister.getName());
        touristDestinationToUpdate.setCity(new City(dtoTouristDestinationRegister.getCityId()));
        // Actualiza otros campos según sea necesario

        touristDestinationService.save(touristDestinationToUpdate);
        Map<String, String> response = new HashMap<>();
        response.put("message", "TouristDestination updated");
        return ResponseEntity.ok(response);

}}
