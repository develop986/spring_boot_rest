package com.mysv986.spring_boot_rest.controller;

import com.mysv986.spring_boot_rest.entity.Place;
import com.mysv986.spring_boot_rest.service.PlaceService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "place")
public class PlaceController {

    private final PlaceService service;

    public PlaceController(PlaceService service) {
        this.service = service;
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Place> id(@PathVariable(value = "id") Long id) {
        Optional<Place> place = service.findById(id);
        return place.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Place>> list() {
        List<Place> places = service.findAll();
        return ResponseEntity.ok(places);
    }

    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String store(@RequestBody Place place) {
        log.debug("place " + place);
        service.store(place);
        return "success";
    }

    @DeleteMapping(path = "{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String remove(@PathVariable(value = "id") Long id) {
        service.removeById(id);
        return "success";
    }

}
