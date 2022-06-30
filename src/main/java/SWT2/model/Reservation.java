package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;


@Entity
@Table(name="tbl_reservation")
//Automatically generates all important methods  (Getter, Setter, Hashcode...)
@Data
//Automatically generates All-args-constructor
@AllArgsConstructor
//Automatically generates No-args-constructor
@NoArgsConstructor


public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resid")
    private int resid;

    @Column(name = "date")
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private String date;

    @Column(name = "time")
    private String time;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @Override
    public String toString() {
        return "Raum{" +
                "id=" + resid +
                ", Date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", User=" + user.getFull_name() +
                ", room=" + room.getName() +
                '}';
    }

}
