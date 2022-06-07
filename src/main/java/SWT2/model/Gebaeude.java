package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_gebaeude")
//Erstellt alle wichtigen Methoden (Getter, Setter, Hashcode...)
@Data
//Erstellt automatisch einen Konstruktor mit allen Argumenten
@AllArgsConstructor
//Erstellt automatisch einen Konstruktor mit keinen Argumenten
@NoArgsConstructor


public class Gebaeude {

    //ID for Primarykey
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bezeichnung;
    private String strasse;
    private int hausnummer;
    private int plz;
    private String stadt;
//    @JsonIgnore
    @OneToMany(mappedBy = "gebaeude", cascade = CascadeType.ALL)
    private List<Raum> raeume = new ArrayList<>();

    @Override
    public String toString() {
        return "Gebaeude{" +
                "id=" + id +
                ", bezeichnung='" + bezeichnung + '\'' +
                ", strasse='" + strasse + '\'' +
                ", hausnummer=" + hausnummer +
                ", plz=" + plz +
                ", stadt='" + stadt + '\'' +
                ", raeume=" + raeume +
                '}';
    }
}