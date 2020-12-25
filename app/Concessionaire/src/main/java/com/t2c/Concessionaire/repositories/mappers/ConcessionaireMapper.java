package com.t2c.Concessionaire.repositories.mappers;

import com.t2c.Concessionaire.model.Concessionaire;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service //TODO change for the correct TAG
public class ConcessionaireMapper implements RowMapper<Concessionaire> {
    @Override
    public Concessionaire mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Concessionaire.Builder()
                    .withId(resultSet.getInt("concessionaireId"))
                    .withAddress(resultSet.getString("address"))
                    .build();
    }
}
