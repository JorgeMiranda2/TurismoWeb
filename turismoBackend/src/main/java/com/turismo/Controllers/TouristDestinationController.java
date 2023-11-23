package com.turismo.Controllers;


import com.turismo.Dtos.DtosInput.DtoTouristDestinationRegister;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<String> createTouristDestination(@Valid @RequestBody DtoTouristDestinationRegister dtoTouristDestinationRegister){
        System.out.println("Getting a post request to dto touristDestination");

        List<TouristDestination> touristDestinations = dtoTouristDestinationRegister.getNames().stream().map((name)->{
        return new TouristDestination(name,dtoTouristDestinationRegister.getCityId());
        }).collect(Collectors.toList());
        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        System.out.println(touristDestinations);
        //Saving the touristDestination by service

        touristDestinations.stream().forEach((touristDestination -> {
            touristDestinationService.save(touristDestination);
        }));
        //returning the response
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Touristdestination/s created ");
    }

    @GetMapping("/touristdestination")
    public ResponseEntity<Collection<TouristDestination>> listTouristDestinations(){
        System.out.println("Getting a get request to touristDestination");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all touristDestinations by service
        Collection<TouristDestination> touristDestinations = touristDestinationService.list();
        System.out.println(touristDestinations);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(touristDestinations);
    }

    @DeleteMapping("/touristdestination/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<TouristDestination> isTouristDestination = touristDestinationService.getTouristDestinationId(id);
        if(isTouristDestination.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        touristDestinationService.delete(isTouristDestination.get());
        return ResponseEntity.status(HttpStatus.OK).body("TouristDestination deleted");
    }


}
