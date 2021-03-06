package com.t2c.Concessionaire.repositories;

import com.t2c.Concessionaire.exceptions.DatabaseConsistencyException;
import com.t2c.Concessionaire.model.Car;
import com.t2c.Concessionaire.repositories.mappers.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CarRepository {
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private CarMapper carMapper;
    @Autowired
    public CarRepository(DataSource dataSource, CarMapper carMapper) {
        setDataSource(dataSource);
        this.carMapper = carMapper;
    }

    private void setDataSource(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Car> findAll() {
        String sql = "SELECT * FROM cars LEFT JOIN concessionaires ON concessionaires.concessionaireId = cars.concessionaireId";
        return namedJdbcTemplate.query(sql, carMapper);
    }
    public List<Car> findAllByConcessionaire(int concessionaireId) {
        String sql = "SELECT * FROM cars LEFT JOIN concessionaires ON concessionaires.concessionaireId = cars.concessionaireId " +
                "WHERE concessionaires.concessionaireId = :concessionaireId";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("concessionaireId", concessionaireId);
        return namedJdbcTemplate.query(sql, argMap, carMapper);
    }
    public Optional<Car> findById(int carId) {
        String sql = "SELECT * FROM cars LEFT JOIN concessionaires ON concessionaires.concessionaireId = cars.concessionaireId " +
                "WHERE cars.carId = :carId";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("carId", carId);
        try {
            Car car = namedJdbcTemplate.queryForObject(sql, argMap, carMapper);
            return Optional.ofNullable(car);
        }catch (EmptyResultDataAccessException notFoundValue) {
            return Optional.empty();
        }
    }

    public void create(Car newCar) {
        String sql = "INSERT INTO cars (brand, cost, saleDate, arrivalDate, sold," +
                "licensePlate, concessionaireId, price) VALUES (:brand, :cost, :saleDate, :arrivalDate," +
                ":sold, :licensePlate, :concessionaireId, :price)";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("brand", newCar.getBrand());
        argMap.put("cost", newCar.getCost());
        argMap.put("saleDate", newCar.getSaleDate());
        argMap.put("arrivalDate", newCar.getArrivalDate());
        argMap.put("sold", newCar.isSold());
        argMap.put("licensePlate", newCar.getLicensePlate());
        argMap.put("price", newCar.getPrice());
        Integer concessionaireId = (newCar.getConcessionaire() == null)? null: newCar.getConcessionaire().getConcessionaireId();
        argMap.put("concessionaireId", concessionaireId);
        namedJdbcTemplate.update(sql, argMap);
    }
    @Transactional(rollbackFor = Exception.class)
    public void update(Car updatedCar) {
        String sql = "UPDATE cars SET " +
                "saleDate = :saleDate, " +
                "sold = :sold, licensePlate = :licensePlate, " +
                "price = :price WHERE carId = :carId";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("saleDate", updatedCar.getSaleDate());
        argMap.put("sold", updatedCar.isSold());
        argMap.put("licensePlate", updatedCar.getLicensePlate());
        argMap.put("price", updatedCar.getPrice());
        argMap.put("carId", updatedCar.getCarId());

        int numberOfRowsAffected = namedJdbcTemplate.update(sql, argMap);
        if(numberOfRowsAffected != 1)
            throw new DatabaseConsistencyException();
    }
    @Transactional(rollbackFor = Exception.class)
    public void delete(int carId) {
        String sql = "DELETE FROM cars WHERE carId = :carId";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("carId", carId);
        int numberOfRowsAffected = namedJdbcTemplate.update(sql, argMap);
        if(numberOfRowsAffected != 1)
            throw new DatabaseConsistencyException();
    }
}
