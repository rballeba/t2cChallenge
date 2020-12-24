package com.t2c.Concessionaire.model;

import com.t2c.Concessionaire.exceptions.BuildException;
import com.t2c.Concessionaire.exceptions.NotSupportedDataException;

import java.util.Date;

public class Car {
    private int carId;
    private String brand;
    private double cost;
    private Date saleDate;
    private Date arrivalDate;
    private boolean sold;
    private String licensePlate;
    private Concessionaire concessionaireId;

    private void setCarId(int carId) {
        this.carId = carId;
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

    private void setConcessionaireId(Concessionaire concessionaireId) {
        this.concessionaireId = concessionaireId;
    }

    private Car(Builder builder) {
        this.setCarId(builder.carId);
        this.setBrand(builder.brand);
        this.setCost(builder.cost);
        this.setSaleDate(builder.saleDate);
        this.setArrivalDate(builder.arrivalDate);
        this.setSold(builder.sold);
        this.setLicensePlate(builder.licensePlate);
        this.setConcessionaireId(builder.concessionaire);
    }
    public class Builder {
        private int carId;
        private String brand;
        private Double cost;
        private Date saleDate;
        private Date arrivalDate;
        private Boolean sold;
        private String licensePlate;
        private Concessionaire concessionaire;
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
        public Car build() throws BuildException {
            if(brand == null || cost == null || saleDate == null || arrivalDate == null || sold == null)
                throw new BuildException();
            else
                return new Car(this);
        }
    }
}
