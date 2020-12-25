package com.t2c.Concessionaire.services;

import com.t2c.Concessionaire.dto.CarDTO;
import com.t2c.Concessionaire.model.Car;
import com.t2c.Concessionaire.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarsService {
    private CarRepository carRepository;
    @Autowired
    public CarsService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    public void createCar(CarDTO carDTO) {
        Car.Builder carBuilder = new Car.Builder()
                .withArrivalDate(carDTO.arrivalDate)
                .withBrand(carDTO.brand)
                .withCost(carDTO.cost)
                .withSaleDate(carDTO.saleDate)
                .withSoldState(carDTO.sold)
                .withLicensePlate(carDTO.licensePlate);
        /*
        if(carDTO.concessionaireId != null)
           carBuilder.withConcessionaire(
                   new Concessionaire.Builder()
                    .withId(carDTO.concessionaireId)
                    .build()
           );
         */
        Car createdCar = carBuilder.build();
        carRepository.create(createdCar);
    }
    public void updateCar(CarDTO carDTO) {
        Car.Builder carBuilder = new Car.Builder()
                .withId(carDTO.carId)
                .withArrivalDate(carDTO.arrivalDate)
                .withBrand(carDTO.brand)
                .withCost(carDTO.cost)
                .withSaleDate(carDTO.saleDate)
                .withSoldState(carDTO.sold)
                .withLicensePlate(carDTO.licensePlate);
        //TODO concessionarie
        Car createdCar = carBuilder.build();
        carRepository.update(createdCar);
    }

    public void deleteCar(int carId) {
        //TODO Find and if it exists then delete
        carRepository.delete(carId);
    }
}
