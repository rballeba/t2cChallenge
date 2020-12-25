package com.t2c.Concessionaire.repositories.mappers;

import com.t2c.Concessionaire.model.Car;
import com.t2c.Concessionaire.model.Concessionaire;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service //TODO change for the correct TAG
public class CarMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet resultSet, int numRow) throws SQLException {
        Car.Builder carBuilder = new Car.Builder()
                .withId(resultSet.getInt("carId"))
                .withBrand(resultSet.getString("brand"))
                .withCost(resultSet.getDouble("cost"))
                .withSaleDate(resultSet.getDate("saleDate"))
                .withArrivalDate(resultSet.getDate("arrivalDate"))
                .withSoldState(resultSet.getBoolean("sold"))
                .withLicensePlate(resultSet.getString("licensePlate"));
        if(resultSet.getObject("concessionaireId") != null) {
            Concessionaire concessionaire = new Concessionaire.Builder()
                    .withId(resultSet.getInt("concessionaireId"))
                    .withAddress(resultSet.getString("address"))
                    .build();
            carBuilder.withConcessionaire(concessionaire);
        }
        return carBuilder.build();
    }
}
