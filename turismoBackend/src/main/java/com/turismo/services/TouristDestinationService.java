package com.turismo.services;

import com.turismo.Models.TouristDestination;
import com.turismo.Repositories.ITouristDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class TouristDestinationService {


    private final ITouristDestination touristDestinationRepo;
    @Autowired
    public TouristDestinationService(ITouristDestination touristDestinationRepo) {
        this.touristDestinationRepo = touristDestinationRepo;
    }


    public Collection<TouristDestination> list(){
        Collection<TouristDestination> touristDestination;
        touristDestination = touristDestinationRepo.findAll();
        return touristDestination;
    }

    public Optional<TouristDestination> getTouristDestinationId(Long id){
        return touristDestinationRepo.findById(id);
    }

    public Long save(TouristDestination touristDestination){
        TouristDestination savedTag = touristDestinationRepo.save(touristDestination);
        return savedTag.getId();
    }

    public void delete(TouristDestination touristDestination){
        touristDestinationRepo.delete(touristDestination);
    }


}
