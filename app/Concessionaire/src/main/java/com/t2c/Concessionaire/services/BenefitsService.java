package com.t2c.Concessionaire.services;

import com.t2c.Concessionaire.dto.ReportDTO;
import com.t2c.Concessionaire.exceptions.EntityNotFoundException;
import com.t2c.Concessionaire.model.Benefits;
import com.t2c.Concessionaire.model.Car;
import com.t2c.Concessionaire.model.Concessionaire;
import com.t2c.Concessionaire.model.Transaction;
import com.t2c.Concessionaire.repositories.CarRepository;
import com.t2c.Concessionaire.repositories.ConcessionaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BenefitsService {
    private CarRepository carRepository;
    private ConcessionaireRepository concessionaireRepository;
    @Autowired
    public BenefitsService(CarRepository carRepository, ConcessionaireRepository concessionaireRepository) {
        this.carRepository = carRepository;
        this.concessionaireRepository = concessionaireRepository;
    }
    public ReportDTO getOverallBenefits() {
        List<Car> cars =  carRepository.findAll();
        return generateReportFromCars(cars);
    }
    public ReportDTO getBenefitsOfConcessionaire(int concessionaireId) {
        Optional<Concessionaire> concessionaireOpt = concessionaireRepository.findById(concessionaireId);
        if(concessionaireOpt.isEmpty())
            throw new EntityNotFoundException();
        List<Car> cars =  carRepository.findAllByConcessionaire(concessionaireId);
        return generateReportFromCars(cars);
    }
    private ReportDTO generateReportFromCars(List<Car> cars) {
        List<Transaction> transactionsDone = cars.stream().map(car -> new Transaction(car.getArrivalDate(),
                (car.isSold())?car.getSaleDate():null, car.getCost(), car.getPrice())).collect(Collectors.toList());
        Benefits benefits = new Benefits(transactionsDone);
        return benefits.generateReport();
    }
}
