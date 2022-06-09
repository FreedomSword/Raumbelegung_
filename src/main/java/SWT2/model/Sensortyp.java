package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_sensortyp")

@Data

@AllArgsConstructor

@NoArgsConstructor



public class Sensortyp {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String bezeichnung;

        @OneToMany(mappedBy = "sensortyp", cascade = CascadeType.ALL)
        private List<Sensor> sensors = new ArrayList<>();


        @Override
        public String toString() {
                return "Sensortyp{" +
                        "id =" + id +
                        ", Bezeichung='" + bezeichnung + '\''  +
                        '}';
        }
    }

