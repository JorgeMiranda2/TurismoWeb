package com.turismo.services;

import com.turismo.Models.Lodging;
import com.turismo.Repositories.ILodging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


@Service
public class LodgingService {


    private ILodging lodgingRepo;

    @Autowired
    public LodgingService(ILodging lodgingRepo) {
        this.lodgingRepo = lodgingRepo;
    }


    public Collection<Lodging> list(){
        Collection<Lodging> lodging;
        lodging = lodgingRepo.findAll();
        return lodging;
    }

    public Optional<Lodging> getLodgingId(Long id){
        return lodgingRepo.findById(id);
    }

    public Long save(Lodging lodging){
        Lodging savedLodging = lodgingRepo.save(lodging);
        return savedLodging.getId();
    }

    public void delete(Lodging lodging){
        lodgingRepo.delete(lodging);
    }


}
