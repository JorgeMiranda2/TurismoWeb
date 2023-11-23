package com.turismo.Controllers;

import com.turismo.Dtos.DtosInput.DtoTouristPlanRegister;
import com.turismo.Models.TouristPlan;
import com.turismo.services.TouristPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api")
public class TouristPlanController {


    @Autowired
    private TouristPlanService touristPlanService;

    public TouristPlanController(TouristPlanService touristPlanService) {
        this.touristPlanService = touristPlanService;
    }

    @PostMapping("/touristplan")
    public ResponseEntity<String> create(@RequestBody TouristPlan touristPlan){
        System.out.println("Getting a post request to touristPlan");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        System.out.println(touristPlan);
        //Saving the touristPlan by service
        Long touristPlanId = touristPlanService.save(touristPlan);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(touristPlanId).toUri();
        //returning the response
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Touristdestination created in: " + location);
    }

    @PostMapping("/touristplanadmin")
    public ResponseEntity<String> createTouristPlan(@RequestBody DtoTouristPlanRegister dtoTouristPlanRegister){
        System.out.println("Getting a post request to dto touristPlan");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        TouristPlan touristPlan = new TouristPlan(dtoTouristPlanRegister);
        System.out.println(touristPlan);
        //Saving the touristPlan by service
        Long touristPlanId = touristPlanService.save(touristPlan);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(touristPlanId).toUri();
        //returning the response
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("Touristdestination created in: " + location);
    }

    @GetMapping("/touristplan")
    public ResponseEntity<Collection<TouristPlan>> listTouristPlan(){
        System.out.println("Getting a get request to touristPlan");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all touristPlan by service
        Collection<TouristPlan> touristPlan = touristPlanService.list();
        System.out.println(touristPlan);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(touristPlan);
    }

    @DeleteMapping("/touristplan/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<TouristPlan> isTouristPlan = touristPlanService.getTouristPlanId(id);
        if(isTouristPlan.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        touristPlanService.delete(isTouristPlan.get());
        return ResponseEntity.status(HttpStatus.OK).body("touristPlan  deleted");
    }

}
