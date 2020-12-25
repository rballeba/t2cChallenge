package com.t2c.Concessionaire.endpoints.controllers;

import com.t2c.Concessionaire.dto.ConcessionaireDTO;
import com.t2c.Concessionaire.exceptions.EntityConsistencyError;
import com.t2c.Concessionaire.exceptions.EntityNotFoundException;
import com.t2c.Concessionaire.services.ConcessionairesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/concessionaires")
public class Concessionaires {
    private ConcessionairesService concessionairesService;
    @Autowired
    public Concessionaires(ConcessionairesService concessionairesService) {
        this.concessionairesService = concessionairesService;
    }
    @GetMapping("")
    public List<ConcessionaireDTO> getAllConcessionaires() {
        return concessionairesService.findConcessionaires();
    }
    @RequestMapping(value = "/{concessionaireId}", method = RequestMethod.GET)
    public ResponseEntity<ConcessionaireDTO> getConcessionaireById(@PathVariable("concessionaireId") int concessionaireId) {
        Optional<ConcessionaireDTO> concessionaireDTOOpt = concessionairesService.findConcessionaireById(concessionaireId);
        if(concessionaireDTOOpt.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok().body(concessionaireDTOOpt.get());
    }
    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<Void> createConcessionaire(@RequestBody ConcessionaireDTO concessionaireDTO) {
        try {
            concessionairesService.createConcessionaire(concessionaireDTO);
        }catch (EntityConsistencyError entityConsistencyError) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value = "/{concessionaireId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteConcessionaireById(@PathVariable("concessionaireId") int concessionaireId) {
        try {
            concessionairesService.deleteConcessionaire(concessionaireId);
        }catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
