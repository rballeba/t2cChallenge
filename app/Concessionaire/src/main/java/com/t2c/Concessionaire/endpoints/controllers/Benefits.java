package com.t2c.Concessionaire.endpoints.controllers;

import com.t2c.Concessionaire.dto.ReportDTO;
import com.t2c.Concessionaire.exceptions.EntityNotFoundException;
import com.t2c.Concessionaire.services.BenefitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benefits")
public class Benefits {
    private BenefitsService benefitsService;
    @Autowired
    public Benefits(BenefitsService benefitsService) {
        this.benefitsService = benefitsService;
    }
    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<ReportDTO> getAllCarsOrdered(@RequestParam(required = false) Integer concessionaireId) {
        if(concessionaireId != null) {
            try {
                return ResponseEntity.ok(benefitsService.getBenefitsOfConcessionaire(concessionaireId));
            }catch (EntityNotFoundException entityNotFoundException) {
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(benefitsService.getOverallBenefits());
    }
}
