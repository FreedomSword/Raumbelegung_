package SWT2.repository;

import SWT2.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    //Find all Rooms where Building = x
    @Query("SELECT r FROM Room r WHERE r.building.bid = ?1")
    List<Room> findRooms(int id);


}

