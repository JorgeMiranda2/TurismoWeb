package com.turismo.Controllers;



import com.turismo.Models.Profile;
import com.turismo.Models.User;
import com.turismo.services.UserService;
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

@RestController
@Validated
@RequestMapping("/api")
public class UserController {

    //Revisar si hay otro metodo del repo para detectar si existe un registro con ese id que no devuelva el registro, solo un true o false
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody User user){
        System.out.println("Getting a post request to user");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        System.out.println(user);
        //Saving the user by service
        Long userId = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userId).toUri();
        //returning the response
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body("User created in: " + location);
    }

    @GetMapping("/user")
    public ResponseEntity<List<User>> listUser(){
        System.out.println("Getting a get request to user");

        //Managing headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "Application/json");

        //Getting all users by service
        List<User> users = userService.list();
        System.out.println(users);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> listUserById(@PathVariable Long id){
        System.out.println("Getting by id");
        Optional<User> userObtained = userService.getUserById(id);
        return userObtained.map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
                .orElseGet(() -> ResponseEntity.unprocessableEntity().build());

    }




    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@Valid @PathVariable Long id, @RequestBody User user){
        Optional<User> isUser = userService.getUserById(id);
        if(isUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        user.setId(isUser.get().getId());
        if(user.getProfile() == null || user.getProfile().getId() == null){
            user.setProfile(new Profile());
            user.getProfile().setId(1L);
        }
        System.out.println(user);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Register updated!");
    }

    @DeleteMapping("/user/{id}")

    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        Optional<User> isUser = userService.getUserById(id);
        if(isUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        userService.delete(isUser.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

}
