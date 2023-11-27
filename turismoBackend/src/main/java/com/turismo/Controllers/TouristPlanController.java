package com.turismo.Controllers;

import com.sun.tools.jconsole.JConsoleContext;
import com.turismo.Dtos.DtosInput.DtoTouristDestinationRegister;
import com.turismo.Dtos.DtosInput.DtoTouristPlanRegister;
import com.turismo.Dtos.DtosOutput.DtoTouristDestination;
import com.turismo.Dtos.DtosOutput.DtoTouristPlanQuota;
import com.turismo.Models.City;
import com.turismo.Models.TouristDestination;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

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
    public ResponseEntity<Map<String, String>> createTouristPlan(@RequestBody DtoTouristPlanRegister dtoTouristPlanRegister){
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
        Map<String, String> response = new HashMap<>();
        response.put("message", "Touristdestination created in: " + location);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(response);
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


    @GetMapping("/enroll")
    public ResponseEntity<Map<String, String>> enroll(){
        //Managing headers


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Long> userId = userService.getUserIdFromUserName(userName);

        //returning the response
        Map<String, String> response = new HashMap<>();
        response.put("message", "register successful: ");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }


    @GetMapping("/quotastouristplan")
    public ResponseEntity<Collection<DtoTouristPlanQuota>> listquotasTouristPlan(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Long> userId = userService.getUserIdFromUserName(userName);

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all touristPlan by service
        Collection<TouristPlan> touristPlans = touristPlanService.list();
        Collection<DtoTouristPlanQuota> quotas = new ArrayList<>();

        for (TouristPlan plan : touristPlans) {
            int usersCount = userService.countUsersByTouristPlan(plan);
            DtoTouristPlanQuota quotaDTO = getDtoTouristPlanQuota(plan, usersCount);

            quotas.add(quotaDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(quotas);
    }

    private static DtoTouristPlanQuota getDtoTouristPlanQuota(TouristPlan plan, int usersCount) {
        int availableQuotas = plan.getNumEnabledPackages() - usersCount;

        DtoTouristPlanQuota quotaDTO = new DtoTouristPlanQuota();
        quotaDTO.setId(plan.getId());
        quotaDTO.setName(plan.getName());
        quotaDTO.setTransportType(plan.getTransportType());
        quotaDTO.setAvailableQuotas(availableQuotas);
        quotaDTO.setTouristDestinations(plan.getTouristDestinations());
        quotaDTO.setDays(plan.getDays());
        quotaDTO.setNights(plan.getNights());
        quotaDTO.setPrice(plan.getPrice());
        return quotaDTO;
    }

    @GetMapping("/quotastouristplanbyuser")
    public ResponseEntity<Collection<DtoTouristPlanQuota>> listquotasTouristPlan2(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Long> userId = userService.getUserIdFromUserName(userName);
        Optional<User> user = userService.getUserById(userId.get());
        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all touristPlan by service
        Collection<TouristPlan> touristPlans = touristPlanService.list();
        Collection<DtoTouristPlanQuota> quotas = new ArrayList<>();


            for (TouristPlan planUser : user.get().getTouristPlans()){

                    int usersCount = userService.countUsersByTouristPlan(planUser);
                    DtoTouristPlanQuota quotaDTO = getDtoTouristPlanQuota(planUser, usersCount);
                    quotas.add(quotaDTO);

            }



        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(quotas);
    }

    @GetMapping("/plansbyuser")
    public ResponseEntity<Map<String, Collection<DtoTouristPlanQuota>>> getPlansByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<Long> userId = userService.getUserIdFromUserName(userName);
        Optional<User> user = userService.getUserById(userId.get());

        // Obtener todos los planes turísticos
        Collection<TouristPlan> allTouristPlans = touristPlanService.list();

        // Crear listas para almacenar planes vinculados y no vinculados
        Collection<DtoTouristPlanQuota> linkedPlans = new ArrayList<>();
        Collection<DtoTouristPlanQuota> unlinkedPlans = new ArrayList<>();

        // Obtener planes vinculados y no vinculados
        for (TouristPlan plan : allTouristPlans) {
            boolean isLinked = false;
            for (TouristPlan userPlan : user.get().getTouristPlans()) {
                if (userPlan.getId().equals(plan.getId())) {
                    isLinked = true;
                    break;
                }
            }
            int usersCount = userService.countUsersByTouristPlan(plan);
            DtoTouristPlanQuota quotaDTO = getDtoTouristPlanQuota(plan, usersCount);

            if (isLinked) {
                linkedPlans.add(quotaDTO);
            } else {
                unlinkedPlans.add(quotaDTO);
            }
        }

        // Almacenar ambas listas en un mapa para devolverlas juntas
        Map<String, Collection<DtoTouristPlanQuota>> plansMap = new HashMap<>();
        plansMap.put("linkedPlans", linkedPlans);
        plansMap.put("unlinkedPlans", unlinkedPlans);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(plansMap);
    }


    @GetMapping("/vinculatetouristplan/{touristPlanId}")
    public ResponseEntity<Map<String, String>> vinculateTouristPlan(@PathVariable Long touristPlanId){
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

                    Map<String, String> response = new HashMap<>();
                    response.put("message", "updated successful");
                    return ResponseEntity.ok().body(response);
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

    @PutMapping("/touristplan/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable Long id, @Valid @RequestBody DtoTouristPlanRegister dtoTouristPlanRegister) {

        Optional<TouristPlan> optionalTouristPlan = touristPlanService.getTouristPlanId(id);

        if (optionalTouristPlan.isPresent()) {
            TouristPlan touristPlan = optionalTouristPlan.get();


            touristPlan.setName(dtoTouristPlanRegister.getName());
            touristPlan.setDays(dtoTouristPlanRegister.getDays());
            touristPlan.setNights(dtoTouristPlanRegister.getNights());
            touristPlan.setPrice(dtoTouristPlanRegister.getPrice());
            touristPlan.setNumEnabledPackages(dtoTouristPlanRegister.getNumEnabledPackages());
            touristPlan.setTransportType(dtoTouristPlanRegister.getTransportType());
            List<TouristDestination> touristDestinations = dtoTouristPlanRegister.getTouristDestinationIds().stream().map(
                    (touristDestinationId)->{
                        return new TouristDestination(touristDestinationId);
                    }
            ).collect(Collectors.toList());
            touristPlan.setTouristDestinations(touristDestinations);
           touristPlanService.save(touristPlan);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Touristplan updated");
            return ResponseEntity.ok(response);
        } else {
            // Manejar el caso en que el ID no se encuentre en la base de datos
            Map<String, String> response = new HashMap<>();
            response.put("message", "Touristplan not found");
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/touristplan/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id){
        Optional<TouristPlan> isTouristPlan = touristPlanService.getTouristPlanId(id);
        if(isTouristPlan.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        touristPlanService.delete(isTouristPlan.get());

        Map<String, String> response = new HashMap<>();
        response.put("message", "Touristplan deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
