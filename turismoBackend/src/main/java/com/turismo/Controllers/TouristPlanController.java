package com.turismo.Controllers;

import com.sun.tools.jconsole.JConsoleContext;
import com.turismo.Dtos.DtosInput.DtoTouristPlanRegister;
import com.turismo.Dtos.DtosOutput.DtoTouristPlanQuota;
import com.turismo.Models.TouristPlan;
import com.turismo.Models.User;
import com.turismo.services.TouristPlanService;
import com.turismo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api")
public class TouristPlanController {


    private final UserService userService;
    private final TouristPlanService touristPlanService;
    @Autowired
    public TouristPlanController(UserService userService, TouristPlanService touristPlanService) {
        this.userService = userService;
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
    @GetMapping("/quotastouristplan")
    public ResponseEntity<Collection<DtoTouristPlanQuota>> listquotasTouristPlan(){
        System.out.println("Getting a get request to touristPlan");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all touristPlan by service
        Collection<TouristPlan> touristPlans = touristPlanService.list();
        Collection<DtoTouristPlanQuota> quotas = new ArrayList<>();

        for (TouristPlan plan : touristPlans) {
            int usersCount = userService.countUsersByTouristPlan(plan);
            int availableQuotas = plan.getNumEnabledPackages() - usersCount;

            DtoTouristPlanQuota quotaDTO = new DtoTouristPlanQuota();
            quotaDTO.setId(plan.getId());
            quotaDTO.setName(plan.getName());
            quotaDTO.setAvailableQuotas(availableQuotas);

            quotas.add(quotaDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(quotas);
    }

    @PutMapping("/vinculatetouristplan/{touristPlanId}")
    public ResponseEntity<String> vinculateTouristPlan(@PathVariable Long touristPlanId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Long> userId = userService.getUserIdFromUserName(userName);

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        if (userId.isPresent()){
            Optional<User> optionalUser = userService.getUserById(userId.get());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // Obtener el TouristPlan por su ID
                Optional<TouristPlan> optionalTouristPlan = touristPlanService.getTouristPlanId(touristPlanId);
                System.out.println("optional: "+ optionalTouristPlan);
                System.out.println("id: "+ touristPlanId);
                if (optionalTouristPlan.isPresent()) {
                    TouristPlan touristPlan = optionalTouristPlan.get();

                    // Obtener la lista actual de touristPlans del usuario
                    List<TouristPlan> touristPlans = user.getTouristPlans();

                    // Agregar el nuevo TouristPlan a la lista
                    touristPlans.add(touristPlan);

                    // Actualizar la lista de touristPlans del usuario
                    user.setTouristPlans(touristPlans);

                    // Guardar el usuario actualizado
                   userService.save(user);

                    return ResponseEntity.ok().body("registration succesfull");
                } else {
                    System.out.println("not found optionalTouristPlan");
                    return ResponseEntity.notFound().build();
                }
            } else {
                System.out.println("not found optionalUser");
                return ResponseEntity.notFound().build();
            }
        } else {
            System.out.println("not found userId");
        return ResponseEntity.notFound().build();
    }}

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
