package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
    private long id;
    private String bezeichnung;
    private String strasse;
    private int hausnummer;
    private int plz;
    private String stadt;
}
