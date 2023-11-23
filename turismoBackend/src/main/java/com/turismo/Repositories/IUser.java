package com.turismo.Repositories;


import com.turismo.Models.TouristPlan;
import com.turismo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUser extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);

    @Query(value = "SELECT id FROM user WHERE user_name=?1",nativeQuery=true)
    public Optional<Long> getUserIdFromUserName(String userName);

    int countUsersByTouristPlansContains(TouristPlan touristPlan);

}
