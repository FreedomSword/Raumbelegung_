package SWT2.repository;


import SWT2.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {


    @Query("SELECT r FROM Sensor r WHERE r.raum.id = ?1")
    List<Sensor> findAllSensors(int id);

}
