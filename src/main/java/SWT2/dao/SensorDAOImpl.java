package SWT2.dao;

import SWT2.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SensorDAOImpl implements SensorDAO
{

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int save(Sensor sensor) {
        return jdbcTemplate.update("INSERT INTO tbl_sensor (raum_Id,  typ) VALUES (?,?)", new Object[]{sensor.getRaum(), sensor.getTyp()});
    }

    @Override
    public int update(Sensor sensor, int id) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public List<Sensor> getAll() {
        return null;
    }

    @Override
    public Sensor getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tbl_sensor WHERE ID = ?", new BeanPropertyRowMapper<Sensor>(Sensor.class),id);
    }
}
