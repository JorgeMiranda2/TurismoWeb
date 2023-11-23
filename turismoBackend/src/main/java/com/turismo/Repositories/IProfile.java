package com.turismo.Repositories;

import com.turismo.Models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProfile extends JpaRepository<Profile,Long> {
}
