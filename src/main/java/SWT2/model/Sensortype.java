package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_sensortype")

@Data

@AllArgsConstructor

@NoArgsConstructor



public class Sensortype {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "stid")
        private int stid;
        @Column(name = "name")
        private String name;

        @OneToMany(mappedBy = "sensortype", cascade = CascadeType.ALL)
        private List<Sensor> sensor = new ArrayList<>();


        @Override
        public String toString() {
                return "Sensortyp{" +
                        "id =" + stid +
                        ", Bezeichung='" + name + '\''  +
                        '}';
        }
    }

