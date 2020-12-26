package com.t2c.Concessionaire.services;

import com.t2c.Concessionaire.dto.CarDTO;
import com.t2c.Concessionaire.exceptions.BuildException;
import com.t2c.Concessionaire.exceptions.EntityConsistencyError;
import com.t2c.Concessionaire.exceptions.EntityNotFoundException;
import com.t2c.Concessionaire.model.Car;
import com.t2c.Concessionaire.model.CarComparators.CarArrivalDateComparator;
import com.t2c.Concessionaire.model.CarComparators.CarSaleDateComparator;
import com.t2c.Concessionaire.model.Concessionaire;
import com.t2c.Concessionaire.repositories.CarRepository;
import com.t2c.Concessionaire.repositories.ConcessionaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
        try {
            Car createdCar = carBuilder.build();
            carRepository.create(createdCar);
        }catch(BuildException buildException) {
            throw new EntityConsistencyError();
        }
    }
    public void updateCar(CarDTO carDTO) {
        Optional<Car> carOpt = carRepository.findById(carDTO.carId);
        if(carOpt.isEmpty())
            throw new EntityNotFoundException();
        Car carToUpdate = carOpt.get();
        if(carToUpdate.isSold())
            throw new EntityConsistencyError();
        CarDTO updatedCarDTO = new CarDTO(carToUpdate.getCarId(), carToUpdate.getBrand(), carToUpdate.getCost(),
                carDTO.saleDate, carToUpdate.getArrivalDate(), carDTO.sold, carDTO.licensePlate,
                (carToUpdate.getConcessionaire() != null)?carToUpdate.getConcessionaire().getConcessionaireId():null,
                carDTO.price);
        Car.Builder carBuilder = generateBuilderWithoutId(updatedCarDTO);
        carBuilder.withId(carDTO.carId);
        Car createdCar = carBuilder.build();
        carRepository.update(createdCar);
    }

    public void deleteCar(int carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        if(carOpt.isEmpty())
            throw new EntityNotFoundException();
        if(carOpt.get().isSold())
            throw new EntityConsistencyError();
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
        if(carDTO.price != null)
            carBuilder.withPrice(carDTO.price);
        return carBuilder;
    }
    public Optional<CarDTO> findCarById(int carId) {
        Optional<Car> carOpt = carRepository.findById(carId);
        return carOpt.flatMap(car -> Optional.of(new CarDTO(car.getCarId(), car.getBrand(), car.getCost(),
                car.getSaleDate(), car.getArrivalDate(), car.isSold(), car.getLicensePlate(),
                (car.getConcessionaire() != null)? car.getConcessionaire().getConcessionaireId(): null, car.getPrice())
        ));
    }
    public List<CarDTO> findCars() {
        return carRepository.findAll().stream().map(car -> new CarDTO(car.getCarId(), car.getBrand(), car.getCost(),
                car.getSaleDate(), car.getArrivalDate(), car.isSold(), car.getLicensePlate(),
                (car.getConcessionaire() != null)? car.getConcessionaire().getConcessionaireId(): null, car.getPrice()))
                .collect(Collectors.toList());
    }
    public List<CarDTO> findCarsOrderedByArrivalDate() {
        List<Car> cars = carRepository.findAll();
        Collections.sort(cars, new CarArrivalDateComparator());
        return cars.stream().map(car -> new CarDTO(car.getCarId(), car.getBrand(), car.getCost(),
                car.getSaleDate(), car.getArrivalDate(), car.isSold(), car.getLicensePlate(),
                (car.getConcessionaire() != null)? car.getConcessionaire().getConcessionaireId(): null, car.getPrice()))
                .collect(Collectors.toList());
    }
    public List<CarDTO> findCarsOrderedBySaleDate() {
        List<Car> cars = carRepository.findAll();
        Collections.sort(cars, new CarSaleDateComparator());
        return cars.stream().map(car -> new CarDTO(car.getCarId(), car.getBrand(), car.getCost(),
                car.getSaleDate(), car.getArrivalDate(), car.isSold(), car.getLicensePlate(),
                (car.getConcessionaire() != null)? car.getConcessionaire().getConcessionaireId(): null, car.getPrice()))
                .collect(Collectors.toList());
    }
}
