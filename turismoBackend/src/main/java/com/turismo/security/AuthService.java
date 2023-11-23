package com.turismo.security;


import com.turismo.Dtos.DtosInput.DtoLogin;
import com.turismo.Dtos.DtosInput.DtoRegister;
import com.turismo.Dtos.DtosOutput.DtoResponseLogin;
import com.turismo.Models.Profile;
import com.turismo.Models.User;
import com.turismo.Repositories.IUser;
import com.turismo.services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthService {




    private UserService userService;

    private JWTService jwtService;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private IUser userRepo;
    @Autowired
    public AuthService(UserService userService, JWTService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, IUser userRepo) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
    }


    public DtoResponseLogin login(DtoLogin dtoLogin){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dtoLogin.getUserName(),dtoLogin.getPassword()));
        UserDetails user = userRepo.findByUserName(dtoLogin.getUserName()).orElseThrow();
        String token = jwtService.getToken(user);
        User user2  = userRepo.findByUserName(dtoLogin.getUserName()).orElseThrow();
        System.out.println("user2: " + user2);
        String userName = user.getUsername();
        System.out.println(user.getAuthorities());
        return DtoResponseLogin.builder().token(token).userName(userName).build();
    }

    public Collection getRole(DtoLogin dtoLogin){
        UserDetails user = userRepo.findByUserName(dtoLogin.getUserName()).orElseThrow();
        return user.getAuthorities();
    }

    public DtoResponseLogin register(DtoRegister dtoRegister) {
        return register(dtoRegister, 1L);
    }
    public DtoResponseLogin register(DtoRegister dtoRegister, Long roleId){

        User user = new User();
        user.setProfile(new Profile());
        user.setUserName(dtoRegister.getUserName());
        user.setName(dtoRegister.getName());
        user.setLastName(dtoRegister.getLastName());
        user.setEmail(dtoRegister.getEmail());
        user.setPassword(passwordEncoder.encode(dtoRegister.getPassword()));
        user.getProfile().setId(roleId);
        userService.save(user);

        return DtoResponseLogin.builder()
                .token(jwtService.getToken(user))
                .userName(dtoRegister.getUserName())
                .build();
    }

    public DtoResponseLogin registerAdmin(DtoRegister dtoRegister) {
        return register(dtoRegister,2L);
    }

    public Claims getClaims(String token){
        return jwtService.getAllClaims(token);
    }

    public boolean validateToken(String token){
        return jwtService.isTokenValid(token, null);
    }
}
