package com.t2c.Concessionaire.model;

import com.t2c.Concessionaire.exceptions.BuildException;
import com.t2c.Concessionaire.exceptions.NotSupportedDataException;

import java.util.Date;

public class Car {
    private Integer carId;
    private String brand;
    private double cost;
    private Date saleDate;
    private Date arrivalDate;
    private boolean sold;
    private String licensePlate;
    private Concessionaire concessionaire;
    private Double price;

    public Double getPrice() {
        return price;
    }

    public int getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public double getCost() {
        return cost;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public boolean isSold() {
        return sold;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Concessionaire getConcessionaire() {
        return concessionaire;
    }

    private void setCarId(Integer carId) {
        this.carId = carId;
    }

    private void setPrice(Double price) {
        if(price < 0)
            throw new NotSupportedDataException("The price can not be negative");
        this.price = price;
    }

    private void setBrand(String brand) {
        if(brand.isEmpty())
                throw new NotSupportedDataException("Brand can not be empty");
        this.brand = brand;
    }

    private void setCost(double cost) {
        if(cost < 0)
            throw new NotSupportedDataException("The cost can not be negative");
        this.cost = cost;
    }

    private void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    private void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    private void setSold(boolean sold) {
        this.sold = sold;
    }

    private void setLicensePlate(String licensePlate) {
        // TODO check license plate format is correct
        this.licensePlate = licensePlate;
    }

    private void setConcessionaire(Concessionaire concessionaire) {
        this.concessionaire = concessionaire;
    }

    private Car(Builder builder) {
        this.setCarId(builder.carId);
        this.setBrand(builder.brand);
        this.setCost(builder.cost);
        this.setSaleDate(builder.saleDate);
        this.setArrivalDate(builder.arrivalDate);
        this.setSold(builder.sold);
        this.setLicensePlate(builder.licensePlate);
        this.setConcessionaire(builder.concessionaire);
        this.setPrice(builder.price);
    }
    public static class Builder {
        private Integer carId;
        private String brand;
        private Double cost;
        private Date saleDate;
        private Date arrivalDate;
        private Boolean sold;
        private String licensePlate;
        private Concessionaire concessionaire;
        private Double price;
        public Builder withId(int carId) {
            this.carId = carId;
            return this;
        }
        public Builder withBrand(String brand) {
            this.brand = brand;
            return this;
        }
        public Builder withCost(double cost) {
            this.cost = cost;
            return this;
        }
        public Builder withSaleDate(Date saleDate) {
            this.saleDate = saleDate;
            return this;
        }
        public Builder withArrivalDate(Date arrivalDate) {
            this.arrivalDate = arrivalDate;
            return this;
        }
        public Builder withSoldState(boolean isSold) {
            this.sold = isSold;
            return this;
        }
        public Builder withLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }
        public Builder withConcessionaire(Concessionaire concessionaire) {
            this.concessionaire = concessionaire;
            return this;
        }
        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }
        public Car build() throws BuildException {
            if(brand == null || cost == null || arrivalDate == null || sold == null)
                throw new BuildException();
            else
                return new Car(this);
        }
    }
}
