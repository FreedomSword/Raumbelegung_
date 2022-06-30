package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="tbl_actor")

@Data

@AllArgsConstructor

@NoArgsConstructor



public class Actor {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "aid")
        private int aid;

        @ManyToOne
        private Room room;

        @ManyToOne
        private Actortype actortype;





        @Override
        public String toString() {
                return "Actor{" +
                        "id =" + aid +
                        ", Raum ='" + room.getRid() + '\'' +
                        ", typ='" + actortype.getName() + '\''  +
                        '}';
        }
    }

