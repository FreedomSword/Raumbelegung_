package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_actortype")

@Data

@AllArgsConstructor

@NoArgsConstructor



public class Actortype {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "atid")
        private int atid;
        @Column(name = "name")
        private String name;

        @OneToMany(mappedBy = "actortype", cascade = CascadeType.ALL)
        private List<Actor> actor = new ArrayList<>();


        @Override
        public String toString() {
                return "Aktortyp{" +
                        "id =" + atid +
                        ", Bezeichung='" + name + '\''  +
                        '}';
        }
    }

