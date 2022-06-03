package SWT2.dao;

import SWT2.model.Raum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository


public class RaumDAOImpl implements RaumDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int save(Raum raum) {
        return jdbcTemplate.update("INSERT INTO tbl_raum (bezeichnung, typ, max_belegung, akt_belegung, gebaeudeId) VALUES (?,?,?,?,?)", new Object[] {raum.getBezeichnung(), raum.getTyp(), raum.getMax_belegung(),
                raum.getAkt_belegung(), raum.getGebaeudeId()});
    }

    @Override
    public int update(Raum raum, int id) {
        return jdbcTemplate.update("UPDATE tbl_raum SET bezeichnung = ?, typ = ?, max_belegung = ?, akt_belegung = ?, gebaeudeId = ? WHERE ID = ?", new Object[] {raum.getBezeichnung(), raum.getTyp(), raum.getMax_belegung(),
                raum.getAkt_belegung(), raum.getGebaeudeId(), id});
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM tbl_raum WHERE ID = ?", id);
    }

    @Override
    public List<Raum> getAll() {
        return jdbcTemplate.query("SELECT * FROM tbl_raum", new BeanPropertyRowMapper<Raum>(Raum.class));
    }

    @Override
    public Raum getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tbl_raum WHERE ID = ?", new BeanPropertyRowMapper<Raum>(Raum.class),id);
    }
}
