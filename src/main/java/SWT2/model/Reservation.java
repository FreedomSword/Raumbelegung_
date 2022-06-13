package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate fromDT;

    private LocalDate toDT;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

}
