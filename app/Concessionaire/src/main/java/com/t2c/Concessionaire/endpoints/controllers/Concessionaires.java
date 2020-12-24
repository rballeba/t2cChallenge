package com.t2c.Concessionaire.endpoints.controllers;

import com.t2c.Concessionaire.dto.CarDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Concessionaires {
    @GetMapping("/concessionaires")
    public List<CarDTO> getAllConcessionaires() {
        return List.of();
    }
    @GetMapping("/concessionaires/{concessionaireId}")
    public List<CarDTO> getAllConcessionaireById(@PathVariable("concessionaireId") int concessionaireId) {
        return List.of();
    }
}
