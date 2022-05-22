package com.mysv986.spring_boot_rest.service;

import com.mysv986.spring_boot_rest.entity.Place;

import java.util.List;
import java.util.Optional;

public interface PlaceService {

    Optional<Place> findById(Long id);

    List<Place> findAll();

    void store(Place place);

    void removeById(Long id);
}
