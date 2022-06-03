package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="tbl_sensor")

@Data

@AllArgsConstructor

@NoArgsConstructor



public class Sensor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private int raumID;

    }

