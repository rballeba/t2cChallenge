package com.t2c.Concessionaire.services;

import com.t2c.Concessionaire.dto.ConcessionaireDTO;
import com.t2c.Concessionaire.exceptions.DatabaseConsistencyException;
import com.t2c.Concessionaire.exceptions.EntityConsistencyError;
import com.t2c.Concessionaire.exceptions.EntityNotFoundException;
import com.t2c.Concessionaire.model.Concessionaire;
import com.t2c.Concessionaire.repositories.ConcessionaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConcessionairesService {
    private ConcessionaireRepository concessionaireRepository;
    @Autowired
    public ConcessionairesService(ConcessionaireRepository concessionaireRepository) {
        this.concessionaireRepository = concessionaireRepository;
    }
    public void createConcessionaire(ConcessionaireDTO concessionaireDTO) {
        Concessionaire newConcessionaire = new Concessionaire.Builder().withAddress(concessionaireDTO.address).build();
        try {
            concessionaireRepository.create(newConcessionaire);
        }
        catch(DatabaseConsistencyException exception) {
            throw new EntityConsistencyError();
        }
    }
    public void deleteConcessionaire(int concessionaireId) {
        Optional<Concessionaire> concessionaireOpt = concessionaireRepository.findById(concessionaireId);
        if(concessionaireOpt.isEmpty())
            throw new EntityNotFoundException();
        concessionaireRepository.delete(concessionaireId);
    }
    public Optional<ConcessionaireDTO> findConcessionaireById(int concessionaireId) {
        Optional<Concessionaire> concessionaireOpt = concessionaireRepository.findById(concessionaireId);
        return concessionaireOpt.flatMap(concessionaire -> Optional.of(new ConcessionaireDTO(
                concessionaire.getConcessionaireId(), concessionaire.getAddress())
        ));
    }
    public List<ConcessionaireDTO> findConcessionaires() {
        return concessionaireRepository.findAll().stream().map(concessionaire -> new ConcessionaireDTO(
                concessionaire.getConcessionaireId(), concessionaire.getAddress())).collect(Collectors.toList());
    }
}
