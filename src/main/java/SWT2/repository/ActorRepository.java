package SWT2.repository;


import SWT2.model.Actor;
import SWT2.model.Actortype;
import SWT2.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {


    //Find all sensors where room = x
    @Query("SELECT a FROM Actor a WHERE a.room.rid = ?1")
    List<Actor> findAllActors(int id);

}

