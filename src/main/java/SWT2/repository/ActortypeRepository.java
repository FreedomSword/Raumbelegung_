package SWT2.repository;


import SWT2.model.Actortype;
import SWT2.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActortypeRepository extends JpaRepository<Actortype, Integer> {


    //Find all sensors where room = x
    @Query("SELECT at FROM Actortype at WHERE at.name = ?1")
    Actortype getByName(String name);
}
