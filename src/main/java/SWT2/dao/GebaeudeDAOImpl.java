package SWT2.dao;


import SWT2.model.Gebaeude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class GebaeudeDAOImpl implements GebaeudeDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int save(Gebaeude gebaeude) {
        return jdbcTemplate.update("INSERT INTO tbl_gebaeude (bezeichnung, strasse, hausnummer, plz, stadt) VALUES(?,?,?,?,?)", new Object[] {gebaeude.getBezeichnung(), gebaeude.getStrasse(), gebaeude.getHausnummer(),
                gebaeude.getPlz(), gebaeude.getStadt()});
    }

    @Override
    public int update(Gebaeude gebaeude, int id) {
        return jdbcTemplate.update("UPDATE tbl_gebaeude SET bezeichnung = ?, strasse = ?, hausnummer = ?, plz = ?, stadt = ? WHERE ID = ?", new Object[] {gebaeude.getBezeichnung(), gebaeude.getStrasse(), gebaeude.getHausnummer(), gebaeude.getPlz(), gebaeude.getStadt(), id});
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM tbl_gebaeude WHERE id=?", id);
    }

    @Override
    public List<Gebaeude> getAll() {

        //SQL Befehl und Mapping an das Modell Gebaeude
        return jdbcTemplate.query("SELECT * FROM tbl_gebaeude", new BeanPropertyRowMapper<Gebaeude>(Gebaeude.class));
    }

    @Override
    public Gebaeude getById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM tbl_gebaeude WHERE ID = ?", new BeanPropertyRowMapper<Gebaeude>(Gebaeude.class), id);
    }
}
