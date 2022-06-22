package SWT2.repository;

import SWT2.model.Reservation;
import SWT2.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Reservation r WHERE r.room.rid = ?1")
    List<Reservation> findAllReservations(int id);

    @Query("SELECT r FROM Reservation r WHERE r.date =?1 AND r.room.rid = ?2")
    List<Reservation> findAllByDate(@DateTimeFormat(pattern = "YYYY-MM-DD") String date, int roomId);

    @Query("SELECT r FROM Reservation r WHERE r.user.uid =?1")
    List<Reservation> findAllByUser(int uid);
}
