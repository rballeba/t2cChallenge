package com.t2c.Concessionaire.Services;

import com.t2c.Concessionaire.dto.ReportDTO;
import com.t2c.Concessionaire.model.Car;
import com.t2c.Concessionaire.repositories.CarRepository;
import com.t2c.Concessionaire.repositories.ConcessionaireRepository;
import com.t2c.Concessionaire.services.BenefitsService;
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
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@RunWith(MockitoJUnitRunner.class)
class BenefitsServiceTests {
    @Mock
    private CarRepository carRepository;
    @Mock
    private ConcessionaireRepository concessionaireRepository;
    private BenefitsService benefitsService;
    @BeforeEach
    void beforeTests() {
        benefitsService = new BenefitsService(carRepository, concessionaireRepository);
        Mockito.when(carRepository.findAll()).thenReturn(generateMockedCars());
    }
    @Test
    void getBenefitsShouldBeComputedProperlyWhenHappyPath() {
        Assert.assertEquals(18000, benefitsService.getOverallBenefits().overallBenefit, 0.1);
    }
    @Test
    void generatedReportHasTheCorrectNumberOfTransactionsWhenHappyPath() {
        Assert.assertEquals(2, benefitsService.getOverallBenefits().transactions.size());
    }
    @Test
    void getBenefitsShouldBeComputedProperlyWhenACarIsNotSold() {
        List<Car> mockedCars = new ArrayList<>();
        mockedCars.add(new Car.Builder()
                .withId(1)
                .withBrand("MockedBrand")
                .withCost(10000)
                .withSaleDate(new GregorianCalendar(2010, 2, 11).getTime())
                .withArrivalDate(new GregorianCalendar(2010, 3, 11).getTime())
                .withSoldState(false)
                .withLicensePlate("4321ABC")
                .withPrice(30000)
                .build()
        );
        Mockito.when(carRepository.findAll()).thenReturn(mockedCars);
        Assert.assertEquals(-10000, benefitsService.getOverallBenefits().overallBenefit, 0.1);
    }
    @Test
    void getBenefitsShouldBeComputedProperlyWhenHappyPathAndOneCarIsNotSold() {
        List<Car> mockedCars = generateMockedCars();
        mockedCars.add(new Car.Builder()
                .withId(4)
                .withBrand("MockedBrand")
                .withCost(10000)
                .withSaleDate(new GregorianCalendar(2010, 2, 11).getTime())
                .withArrivalDate(new GregorianCalendar(2010, 3, 11).getTime())
                .withSoldState(false)
                .withLicensePlate("4321ABC")
                .withPrice(30000)
                .build()
        );
        Mockito.when(carRepository.findAll()).thenReturn(mockedCars);
        Assert.assertEquals(8000, benefitsService.getOverallBenefits().overallBenefit, 0.1);
    }
    @Test
    void getBenefitsShouldReturn0TransactionsAnd0BenefitsWhenThereIsNoCars() {
        Mockito.when(carRepository.findAll()).thenReturn(Collections.emptyList());
        ReportDTO report = benefitsService.getOverallBenefits();
        Assert.assertEquals(0, report.overallBenefit, 0.1);
        Assert.assertEquals(0, report.transactions.size());
    }

    private List<Car> generateMockedCars() {
        List<Car> mockedCars = new ArrayList<>();
        mockedCars.add(new Car.Builder()
                .withId(1)
                .withBrand("MockedBrand")
                .withCost(10000)
                .withSaleDate(new GregorianCalendar(2010, 2, 11).getTime())
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
                .withArrivalDate(new GregorianCalendar(2010, 3, 11).getTime()).withSoldState(true)
                .withLicensePlate("4321ABC")
                .withPrice(3000)
                .build()
        );
        return mockedCars;
    }
}