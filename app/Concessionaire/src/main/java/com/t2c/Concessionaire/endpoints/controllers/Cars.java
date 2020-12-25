package com.t2c.Concessionaire.endpoints.controllers;

import com.t2c.Concessionaire.dto.CarDTO;
import com.t2c.Concessionaire.exceptions.EntityConsistencyError;
import com.t2c.Concessionaire.exceptions.EntityNotFoundException;
import com.t2c.Concessionaire.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class Cars {
    private CarsService carsService;
    @Autowired
    public Cars(CarsService carsService) {
        this.carsService = carsService;
    }
    @GetMapping(value = "", produces = "application/json")
    public List<CarDTO> getAllCars() {
        return carsService.findCars();
    }
    @RequestMapping(value = "/{carId}", method = RequestMethod.GET)
    public ResponseEntity<CarDTO> getCarById(@PathVariable("carId") int carId) {
        Optional<CarDTO> carDTOOpt = carsService.findCarById(carId);
        if(carDTOOpt.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(carDTOOpt.get());    }
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Void> createCar(@RequestBody CarDTO carDTO) {
        try {
            carsService.createCar(carDTO);
        }catch (EntityConsistencyError entityConsistencyError) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/{carId}", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCar(@PathVariable("carId") int carId,@RequestBody CarDTO carDTO) {
        if(carDTO.carId != null && carDTO.carId != carId)
            return ResponseEntity.badRequest().build();
        carDTO.carId = carId;
        try {
            carsService.updateCar(carDTO);
        }catch (EntityConsistencyError entityConsistencyError) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/{carId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCarById(@PathVariable("carId") int carId) {
        try {
            carsService.deleteCar(carId);
        }catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}

