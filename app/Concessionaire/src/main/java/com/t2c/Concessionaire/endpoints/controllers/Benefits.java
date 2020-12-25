package com.t2c.Concessionaire.endpoints.controllers;

import com.t2c.Concessionaire.services.BenefitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/benefits")
public class Benefits {
    private BenefitsService benefitsService;
    @Autowired
    public Benefits(BenefitsService benefitsService) {
        this.benefitsService = benefitsService;
    }
}
