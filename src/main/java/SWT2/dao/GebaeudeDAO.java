package SWT2.dao;

import SWT2.model.Gebaeude;

import java.util.List;


public interface GebaeudeDAO {

    int save(Gebaeude gebaeude);

    int update(Gebaeude gebaeude, int id);

    int delete(int id);

    List<Gebaeude> getAll();

    Gebaeude getById(int id);
}
