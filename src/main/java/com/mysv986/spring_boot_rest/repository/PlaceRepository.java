package com.mysv986.spring_boot_rest.repository;

import com.mysv986.spring_boot_rest.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
