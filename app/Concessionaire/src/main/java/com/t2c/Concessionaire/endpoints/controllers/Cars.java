package com.t2c.Concessionaire.endpoints.controllers;

import com.t2c.Concessionaire.dto.CarDTO;
import com.t2c.Concessionaire.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return List.of();
    }
    @RequestMapping(value = "/{carId}", method = RequestMethod.GET)
    public CarDTO getCarById(@PathVariable("carId") int carId) {
        return new CarDTO(1, "Ferrari", 1.5, java.util.Calendar.getInstance().getTime(), java.util.Calendar.getInstance().getTime(), true, "12234AX", null);
    }
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Void> createCar(@RequestBody CarDTO carDTO) {
        carsService.createCar(carDTO);
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/{carId}", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateCar(@PathVariable("carId") int carId,@RequestBody CarDTO carDTO) {
        if(carDTO.carId != null && carDTO.carId != carId)
            return ResponseEntity.badRequest().build();
        carDTO.carId = carId;
        carsService.updateCar(carDTO);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(value = "/{carId}")
    public ResponseEntity<Void> deleteCarById(@PathVariable("carId") int carId) {
        carsService.deleteCar(carId);
        return ResponseEntity.ok().build();
    }
}

