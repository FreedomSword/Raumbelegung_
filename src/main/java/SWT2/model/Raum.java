package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_raum")
//Erstellt alle wichtigen Methoden (Getter, Setter, Hashcode...)
@Data
//Erstellt automatisch einen Konstruktor mit allen Argumenten
@AllArgsConstructor
//Erstellt automatisch einen Konstruktor mit keinen Argumenten
@NoArgsConstructor

public class Raum {

    //ID for Primarykey
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bezeichnung;
    private String typ;
    private int max_belegung;
    private int akt_belegung;

    @ManyToOne
    private Gebaeude gebaeude;

    @OneToMany(mappedBy = "raum", cascade = CascadeType.ALL)
    private List<Sensor> sensors = new ArrayList<>();

    @Override
    public String toString() {
        return "Raum{" +
                "id=" + id +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", typ='" + typ + '\'' +
                ", max_belegung=" + max_belegung +
                ", akt_belegung=" + akt_belegung +
                ", gebaeude=" + gebaeude.getId() +
                '}';
    }
}
