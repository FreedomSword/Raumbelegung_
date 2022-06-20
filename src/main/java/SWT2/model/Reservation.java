package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.Date;


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

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date fromDT;

    @Column(name = "from_time")
    private String fromTime;
    @Column(name = "to_time")
    private String toTime;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

}
