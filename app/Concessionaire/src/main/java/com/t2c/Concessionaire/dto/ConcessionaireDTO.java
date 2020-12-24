package com.t2c.Concessionaire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConcessionaireDTO {
    @JsonProperty("concessionaireId")
    int concessionaireId;
    @JsonProperty("address")
    String address;

    public ConcessionaireDTO(int concessionaireId, String address) {
        this.concessionaireId = concessionaireId;
        this.address = address;
    }
}
