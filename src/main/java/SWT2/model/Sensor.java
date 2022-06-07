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
        private int id;

        @ManyToOne
        private Raum raum;

        private int typ;


        @Override
        public String toString() {
                return "Sensor{" +
                        "id =" + id +
                        ", Raum ='" + raum.getId() + '\'' +
                        ", typ='" + typ + '\''  +
                        '}';
        }
    }

