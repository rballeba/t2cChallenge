package com.t2c.Concessionaire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConcessionaireDTO {
    @JsonProperty("concessionaireId")
    Integer concessionaireId;
    @JsonProperty("address")
    String address;

    public ConcessionaireDTO(Integer concessionaireId, String address) {
        this.concessionaireId = concessionaireId;
        this.address = address;
    }
}
