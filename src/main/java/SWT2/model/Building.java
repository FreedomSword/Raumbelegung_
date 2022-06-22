package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_building")
//Automatically generates all important methods  (Getter, Setter, Hashcode...)
@Data
//Automatically generates All-args-constructor
@AllArgsConstructor
//Automatically generates No-args-constructor
@NoArgsConstructor


public class Building {

    //ID for Primarykey
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid")
    private int bid;
    @Column(name = "name")
    private String name;
    @Column(name = "street")
    private String street;
    @Column(name = "housenumber")
    private int houseNumber;
    @Column(name = "postalcode")
    private int postalCode;
    @Column(name = "city")
    private String city;

    @Column(nullable = true)
    private String photo;


    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    private List<Room> room = new ArrayList<>();

    @Override
    public String toString() {
        return "Gebaeude{" +
                "id=" + bid +
                ", bezeichnung='" + name + '\'' +
                ", strasse='" + street + '\'' +
                ", hausnummer=" + houseNumber +
                ", plz=" + postalCode +
                ", stadt='" + city + '\'' +
                ", raeume=" + room +
                '}';
    }
}