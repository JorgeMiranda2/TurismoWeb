package com.turismo.Controllers;


import com.turismo.Dtos.DtosInput.DtoLodgingRegister;
import com.turismo.Dtos.DtosInput.DtoTouristDestinationRegister;
import com.turismo.Dtos.DtosOutput.DtoLodgings;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
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
    public ResponseEntity<String> create(@RequestBody DtoLodgingRegister dtoLodgingRegister){
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
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Touristdestination created in: " + location);
    }

    @GetMapping("/lodging")
    public ResponseEntity<Collection<Lodging>> listlodgings(){
        System.out.println("Getting a get request to lodging");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all lodgings by service
        Collection<Lodging> lodgings = lodgingService.list();
        System.out.println(lodgings);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(lodgings);
    }

    @DeleteMapping("/lodging/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Lodging> isLoding = lodgingService.getLodgingId(id);
        if(isLoding.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        lodgingService.delete(isLoding.get());
        return ResponseEntity.status(HttpStatus.OK).body("lodging  deleted");
    }



}
