package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_room")
//Erstellt alle wichtigen Methoden (Getter, Setter, Hashcode...)
@Data
//Erstellt automatisch einen Konstruktor mit allen Argumenten
@AllArgsConstructor
//Erstellt automatisch einen Konstruktor mit keinen Argumenten
@NoArgsConstructor

public class Room {

    //ID for Primarykey
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private int rid;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "max_occupancy")
    private int max_occupancy;
    @Column(name = "cur_occupancy")
    private int cur_occupancy;

    @ManyToOne
    private Building building;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Sensor> sensor = new ArrayList<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @Override
    public String toString() {
        return "Raum{" +
                "id=" + rid +
                ", bezeichnung='" + name + '\'' +
                ", typ='" + type + '\'' +
                ", max_belegung=" + max_occupancy +
                ", akt_belegung=" + cur_occupancy +
                ", gebaeude=" + building.getBid() +
                '}';
    }
}
