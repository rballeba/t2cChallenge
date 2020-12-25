package com.t2c.Concessionaire.endpoints.controllers;

import com.t2c.Concessionaire.dto.CarDTO;
import com.t2c.Concessionaire.dto.ConcessionaireDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concessionaires")
public class Concessionaires {
    @GetMapping("")
    public List<CarDTO> getAllConcessionaires() {
        return List.of();
    }
    @GetMapping("/concessionaires/{concessionaireId}")
    public List<CarDTO> getAllConcessionaireById(@PathVariable("concessionaireId") int concessionaireId) {
        return List.of();
    }
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Void> createConcessionaire(@RequestBody ConcessionaireDTO concessionaireDTO) {
        return ResponseEntity.ok().build();
    }
}
