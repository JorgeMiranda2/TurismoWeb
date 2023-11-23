package com.turismo.services;

import com.turismo.Models.TouristPlan;
import com.turismo.Repositories.ITouristPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TouristPlanService {


    private final ITouristPlan touristPlanRepo;

    @Autowired
    public TouristPlanService(ITouristPlan touristPlanRepo) {
        this.touristPlanRepo = touristPlanRepo;
    }


    public Collection<TouristPlan> list(){
        Collection<TouristPlan> touristPlan;
        touristPlan = touristPlanRepo.findAll();
        return touristPlan;
    }

    public Optional<TouristPlan> getTouristPlanId(Long id){
        return touristPlanRepo.findById(id);
    }

    public Long save(TouristPlan touristPlan){
        TouristPlan savedTag = touristPlanRepo.save(touristPlan);
        return savedTag.getId();
    }

    public void delete(TouristPlan touristPlan){
        touristPlanRepo.delete(touristPlan);
    }

}
