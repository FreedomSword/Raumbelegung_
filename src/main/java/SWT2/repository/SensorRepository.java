package SWT2.repository;


import SWT2.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {


    //Find all sensors where room = x
    @Query("SELECT s FROM Sensor s WHERE s.room.rid = ?1")
    List<Sensor> findAllSensors(int id);
}
