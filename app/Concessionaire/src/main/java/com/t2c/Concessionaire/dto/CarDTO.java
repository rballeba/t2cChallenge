package com.t2c.Concessionaire.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CarDTO {
    @JsonProperty("carId")
    int carId;
    @JsonProperty("brand")
    String brand;
    @JsonProperty("cost")
    double cost;
    @JsonProperty("saleDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    Date saleDate;
    @JsonProperty("arrivalDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    Date arrivalDate;
    @JsonProperty("sold")
    boolean sold;
    @JsonProperty("licensePlate")
    String licensePlate;
    @JsonProperty("concessionaireId")
    Integer concessionaireId;

    public CarDTO(int carId, String brand, double cost, Date saleDate, Date arrivalDate,
                  boolean sold, String licensePlate, Integer concessionaireId) {
        this.carId = carId;
        this.brand = brand;
        this.cost = cost;
        this.saleDate = saleDate;
        this.arrivalDate = arrivalDate;
        this.sold = sold;
        this.licensePlate = licensePlate;
        this.concessionaireId = concessionaireId;
    }
}
