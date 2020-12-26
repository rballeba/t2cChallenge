package com.t2c.Concessionaire.Services;

import com.t2c.Concessionaire.dto.CarDTO;
import com.t2c.Concessionaire.exceptions.EntityConsistencyError;
import com.t2c.Concessionaire.model.Car;
import com.t2c.Concessionaire.repositories.CarRepository;
import com.t2c.Concessionaire.repositories.ConcessionaireRepository;
import com.t2c.Concessionaire.services.CarsService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@RunWith(MockitoJUnitRunner.class)
class CarsServiceTests {
    @Mock
    private CarRepository carRepository;
    @Mock
    private ConcessionaireRepository concessionaireRepository;
    private CarsService carsService;
    @BeforeEach
    public void beforeTests() {
        carsService = new CarsService(carRepository, concessionaireRepository);
        Mockito.when(carRepository.findAll()).thenReturn(generateMockedCars());
    }
    @Test
    void aCarCanNotBeUpdatedIfItIsSold() {
        Mockito.when(carRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getSoldCar()));
        Exception exception = Assert.assertThrows(EntityConsistencyError.class, () -> {
            carsService.updateCar(getArbitraryCarDTO());
        });
    }
    @Test
    void aCarCanNotBeDeletedIfItIsSold() {
        Mockito.when(carRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(getSoldCar()));
        Exception exception = Assert.assertThrows(EntityConsistencyError.class, () -> {
            carsService.deleteCar(getArbitraryCarDTO().carId);
        });
    }
    @Test
    void carsAreOrderedByArrivalDateWhenRequested() {
        List<CarDTO> orderedCarsByArrival = carsService.findCarsOrderedByArrivalDate();
        for(int i=0; i < orderedCarsByArrival.size() - 1; i++) {
            boolean condition = orderedCarsByArrival.get(i).arrivalDate.after(orderedCarsByArrival.get(i+1).arrivalDate) ||
                    orderedCarsByArrival.get(i).arrivalDate.equals(orderedCarsByArrival.get(i+1).arrivalDate);
            Assert.assertTrue(condition);
        }
    }
    @Test
    void carsAreOrderedBySaleDateWhenRequested() {
        List<CarDTO> orderedCarsByArrival = carsService.findCarsOrderedBySaleDate();
        for(int i=0; i < orderedCarsByArrival.size() - 1; i++) {
            if(orderedCarsByArrival.get(i).saleDate != null) {
                boolean condition = orderedCarsByArrival.get(i).saleDate.after(orderedCarsByArrival.get(i + 1).saleDate) ||
                        orderedCarsByArrival.get(i).saleDate.equals(orderedCarsByArrival.get(i + 1).saleDate);
                Assert.assertTrue(condition);
            }
        }
    }
    private Car getSoldCar() {
        return new Car.Builder()
                .withId(4)
                .withBrand("MockedBrand")
                .withCost(10000)
                .withSaleDate(new GregorianCalendar(2010, 2, 11).getTime())
                .withArrivalDate(new GregorianCalendar(2010, 3, 11).getTime())
                .withSoldState(true)
                .withLicensePlate("4321ABC")
                .withPrice(30000)
                .build();
    }
    private CarDTO getArbitraryCarDTO() {
        return new CarDTO(4, "Ferrari", 600,
                new GregorianCalendar(2010, 2, 11).getTime(),
                new GregorianCalendar(2010, 2, 11).getTime(),
                true, "4321ABB", null, 50000.0);
    }
    private List<Car> generateMockedCars() {
        List<Car> mockedCars = new ArrayList<>();
        mockedCars.add(new Car.Builder()
                .withId(1)
                .withBrand("MockedBrand")
                .withCost(10000)
                .withSaleDate(new GregorianCalendar(2020, 2, 11).getTime())
                .withArrivalDate(new GregorianCalendar(2010, 3, 11).getTime()).withSoldState(true)
                .withLicensePlate("4321ABC")
                .withPrice(30000)
                .build()
        );
        mockedCars.add(new Car.Builder()
                .withId(1)
                .withBrand("MockedBrand")
                .withCost(5000)
                .withSaleDate(new GregorianCalendar(2010, 2, 11).getTime())
                .withArrivalDate(new GregorianCalendar(2015, 3, 11).getTime()).withSoldState(true)
                .withLicensePlate("4321ABC")
                .withPrice(3000)
                .build()
        );
        mockedCars.add(new Car.Builder()
                .withId(1)
                .withBrand("MockedBrand")
                .withCost(5000)
                .withSaleDate(new GregorianCalendar(2012, 2, 11).getTime())
                .withArrivalDate(new GregorianCalendar(2020, 3, 11).getTime()).withSoldState(true)
                .withLicensePlate("4321ABC")
                .withPrice(3000)
                .build()
        );
        return mockedCars;
    }
}
