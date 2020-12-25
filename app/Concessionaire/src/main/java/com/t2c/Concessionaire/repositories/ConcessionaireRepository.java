package com.t2c.Concessionaire.repositories;

import com.t2c.Concessionaire.exceptions.DatabaseConsistencyException;
import com.t2c.Concessionaire.exceptions.EntityConsistencyError;
import com.t2c.Concessionaire.model.Concessionaire;
import com.t2c.Concessionaire.repositories.mappers.ConcessionaireMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
public class ConcessionaireRepository {
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private ConcessionaireMapper concessionaireMapper;

    @Autowired
    public ConcessionaireRepository(DataSource dataSource, ConcessionaireMapper concessionaireMapper) {
        setDataSource(dataSource);
        this.concessionaireMapper = concessionaireMapper;
    }

    private void setDataSource(DataSource dataSource) {
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Concessionaire> findAll() {
        String sql = "SELECT * FROM concessionaires";
        return namedJdbcTemplate.query(sql, concessionaireMapper);
    }

    public Optional<Concessionaire> findById(int concessionaireId) {
        String sql = "SELECT * FROM concessionaires WHERE concessionaireId = :concessionaireId";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("concessionaireId", concessionaireId);
        try {
            Concessionaire concessionaire = namedJdbcTemplate.queryForObject(sql, argMap, concessionaireMapper);
            return Optional.ofNullable(concessionaire);
        }catch (EmptyResultDataAccessException notFoundValue) {
            return Optional.empty();
        }
    }

    public void create(Concessionaire newConcessionaire) {
        String sql = "INSERT INTO concessionaires (address) VALUES (:address)";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("address", newConcessionaire.getAddress());
        try {
            namedJdbcTemplate.update(sql, argMap);
        }catch (DuplicateKeyException duplicateKeyException) {
            throw new DatabaseConsistencyException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(int concessionaireId) {
        String sql = "DELETE FROM concessionaires WHERE concessionaireId = :concessionaireId";
        Map<String, Object> argMap = new HashMap<>();
        argMap.put("concessionaireId", concessionaireId);
        int numberOfRowsAffected = namedJdbcTemplate.update(sql, argMap);
        if(numberOfRowsAffected != 1)
            throw new DatabaseConsistencyException();
    }
}
