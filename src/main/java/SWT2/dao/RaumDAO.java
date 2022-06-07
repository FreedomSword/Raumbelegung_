package SWT2.dao;

import SWT2.model.Raum;

import java.util.List;

public interface RaumDAO {

    int save(Raum raum);

    int updateBelegung(Raum raum, int id);


    int update(Raum raum, int id);

    int delete(int id);

    List<Raum> getAll();

    Raum getById(int id);
}
