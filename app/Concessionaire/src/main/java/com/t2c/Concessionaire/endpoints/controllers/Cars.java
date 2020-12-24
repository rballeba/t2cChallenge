package com.t2c.Concessionaire.endpoints.controllers;

import com.t2c.Concessionaire.dto.CarDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Cars {
    @GetMapping("/cars")
    public List<CarDTO> getAllCars() {
        return List.of();
    }
    @GetMapping("/cars/{carId}")
    public CarDTO getCarById(@PathVariable("carId") int carId) {
        return new CarDTO(1, "Ferrari", 1.5, java.util.Calendar.getInstance().getTime(), java.util.Calendar.getInstance().getTime(), true, "12234AX", null);
    }
}

