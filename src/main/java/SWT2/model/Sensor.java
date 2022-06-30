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
        @Column(name = "sid")
        private int sid;

        @ManyToOne
        private Room room;

        @ManyToOne
        private Sensortype sensortype;





        @Override
        public String toString() {
                return "Sensor{" +
                        "id =" + sid +
                        ", Raum ='" + room.getRid() + '\'' +
                        ", typ='" + sensortype.getName() + '\''  +
                        '}';
        }
    }

