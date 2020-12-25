package com.t2c.Concessionaire.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConcessionaireDTO {
    @JsonProperty("concessionaireId")
    public Integer concessionaireId;
    @JsonProperty("address")
    public String address;

    public ConcessionaireDTO(Integer concessionaireId, String address) {
        this.concessionaireId = concessionaireId;
        this.address = address;
    }
}
