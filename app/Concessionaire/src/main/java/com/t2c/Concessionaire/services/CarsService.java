package com.t2c.Concessionaire.services;

import com.t2c.Concessionaire.dto.CarDTO;
import com.t2c.Concessionaire.exceptions.EntityConsistencyError;
import com.t2c.Concessionaire.exceptions.EntityNotFoundException;
import com.t2c.Concessionaire.model.Car;
import com.t2c.Concessionaire.model.Concessionaire;
import com.t2c.Concessionaire.repositories.CarRepository;
import com.t2c.Concessionaire.repositories.ConcessionaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsService {
    private CarRepository carRepository;
    private ConcessionaireRepository concessionaireRepository;
    @Autowired
    public CarsService(CarRepository carRepository, ConcessionaireRepository concessionaireRepository) {
        this.carRepository = carRepository;
        this.concessionaireRepository = concessionaireRepository;
    }
    public void createCar(CarDTO carDTO) {
        Car.Builder carBuilder = generateBuilderWithoutId(carDTO);
        Car createdCar = carBuilder.build();
        carRepository.create(createdCar);
    }
    public void updateCar(CarDTO carDTO) {
        Car.Builder carBuilder = generateBuilderWithoutId(carDTO);
        carBuilder.withId(carDTO.carId);
        Car createdCar = carBuilder.build();
        carRepository.update(createdCar);
    }

    public void deleteCar(int carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if(carOpt.isEmpty())
            throw new EntityNotFoundException();
        carRepository.delete(carId);
    }
    private Car.Builder generateBuilderWithoutId(CarDTO carDTO) {
        Car.Builder carBuilder = new Car.Builder()
                .withArrivalDate(carDTO.arrivalDate)
                .withBrand(carDTO.brand)
                .withCost(carDTO.cost)
                .withSaleDate(carDTO.saleDate)
                .withSoldState(carDTO.sold)
                .withLicensePlate(carDTO.licensePlate);
        if (carDTO.concessionaireId != null) {
            Optional<Concessionaire>  concessionaireOpt = concessionaireRepository.findById(carDTO.concessionaireId);
            if(concessionaireOpt.isEmpty())
                throw new EntityConsistencyError();
            carBuilder.withConcessionaire(concessionaireOpt.get());
        }
        return carBuilder;
    }
    public Optional<CarDTO> findCarById(int carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        return carOpt.flatMap(car -> Optional.of(new CarDTO(car.getCarId(), car.getBrand(), car.getCost(),
                car.getSaleDate(), car.getArrivalDate(), car.isSold(), car.getLicensePlate(),
                (car.getConcessionaire() != null)? car.getConcessionaire().getConcessionaireId(): null)
        ));
    }
    public List<CarDTO> findCars() {
        return carRepository.findAll().stream().map(car -> new CarDTO(car.getCarId(), car.getBrand(), car.getCost(),
                car.getSaleDate(), car.getArrivalDate(), car.isSold(), car.getLicensePlate(),
                (car.getConcessionaire() != null)? car.getConcessionaire().getConcessionaireId(): null))
                .collect(Collectors.toList());
    }
}
