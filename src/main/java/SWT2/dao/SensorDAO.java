package SWT2.dao;

import SWT2.model.Gebaeude;
import SWT2.model.Sensor;

import java.util.List;

public interface SensorDAO {

    int save(Sensor senor);

    int update(Sensor sensor, int id);

    int delete(int id);

    List<Sensor> getAll();

    Sensor getById(int id);
}
