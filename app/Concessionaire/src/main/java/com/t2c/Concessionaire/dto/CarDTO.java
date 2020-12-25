package com.t2c.Concessionaire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CarDTO {
    @JsonProperty("carId")
    public Integer carId;
    @JsonProperty("brand")
    public String brand;
    @JsonProperty("cost")
    public double cost;
    @JsonProperty("saleDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date saleDate;
    @JsonProperty("arrivalDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    public Date arrivalDate;
    @JsonProperty("sold")
    public boolean sold;
    @JsonProperty("licensePlate")
    public String licensePlate;
    @JsonProperty("concessionaireId")
    public Integer concessionaireId;
    @JsonProperty("price")
    public Double price;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarDTO(Integer carId, String brand, double cost, Date saleDate, Date arrivalDate, boolean sold,
                  String licensePlate, Integer concessionaireId, Double price) {
        this.carId = carId;
        this.brand = brand;
        this.cost = cost;
        this.saleDate = saleDate;
        this.arrivalDate = arrivalDate;
        this.sold = sold;
        this.licensePlate = licensePlate;
        this.concessionaireId = concessionaireId;
        this.price = price;
    }
}
