package com.turismo.services;

import com.turismo.Models.TouristPlan;
import com.turismo.Models.User;
import com.turismo.Repositories.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private  IUser userRepo;


    public List<User> list(){
        List<User> users;
        users = userRepo.findAll();
        return users;
    }

    public Optional<User> getUserById(Long id){
        return userRepo.findById(id);
    }

    public Long save(User user){
        User savedUser = userRepo.save(user);
        return savedUser.getId();
    }

    public void delete(User user){
        userRepo.delete(user);
    }


    public Optional<Long> getUserIdFromUserName(String userName){
        Optional<Long> id = userRepo.getUserIdFromUserName(userName);
        return id;
    }

    public int countUsersByTouristPlan(TouristPlan touristPlan) {
        return userRepo.countUsersByTouristPlansContains(touristPlan);
    }
}
