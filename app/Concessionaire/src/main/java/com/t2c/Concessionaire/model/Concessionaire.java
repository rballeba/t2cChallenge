package com.t2c.Concessionaire.model;

import com.t2c.Concessionaire.exceptions.NotSupportedDataException;

public class Concessionaire {
    private Integer concessionaireId;
    private String address;
    private Concessionaire(Builder builder) {
        this.setConcessionaireId(builder.concessionaireId);
        this.setAddress(builder.address);
    }

    public int getConcessionaireId() {
        return concessionaireId;
    }

    public String getAddress() {
        return address;
    }

    private void setConcessionaireId(Integer concessionaireId) {
        this.concessionaireId = concessionaireId;
    }

    private void setAddress(String address) {
        if(address.isEmpty())
            throw new NotSupportedDataException("The address can not be empty");
        this.address = address;
    }

    public static class Builder {
        private Integer concessionaireId;
        private String address;
        public Builder withId(int concessionaireId) {
            this.concessionaireId = concessionaireId;
            return this;
        }
        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }
        public Concessionaire build() {
            return new Concessionaire(this);
        }
    }
}
