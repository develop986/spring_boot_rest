package com.mysv986.spring_boot_rest.service.impl;

import com.mysv986.spring_boot_rest.entity.Place;
import com.mysv986.spring_boot_rest.repository.PlaceRepository;
import com.mysv986.spring_boot_rest.service.PlaceService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository repository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.repository = placeRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Place> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Place> findAll() {
        return repository.findAll();
    }

    @Transactional(timeout = 10)
    @Override
    public void store(Place place) {
        repository.save(place);
    }

    @Transactional(timeout = 10)
    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

}
