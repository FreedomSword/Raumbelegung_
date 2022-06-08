package SWT2.repository;

import SWT2.model.Raum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaumRepository extends JpaRepository<Raum, Integer> {

    @Query("SELECT r FROM Raum r WHERE r.gebaeude.id = ?1")
    List<Raum> findAllRooms(int id);


}

