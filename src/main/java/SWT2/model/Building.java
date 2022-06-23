package SWT2.model;

import SWT2.repository.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || bid == 0) return null;

        return "/building-photos/" + name + "/" + photo;
    }

    @Transient
    public int getAmountRooms() {

        int amountRooms = 0;
        for(int i = 0; i < room.size(); i++) {
            amountRooms++;
        }

        return amountRooms;
    }

    @Transient
    public int getAmountPlaces() {

        int amountPlaces = 0;
        for(int i = 0; i < room.size(); i++) {
           amountPlaces+=room.get(i).getMax_occupancy();
        }

        return amountPlaces;
    }

    @Transient
    public int getFreeRooms() {

        int freeRooms = getAmountRooms();

        for(int i = 0; i < room.size(); i++) {
          if(room.get(i).isReservedNow())
              freeRooms--;
        }

        return freeRooms;
    }

    @Transient
    public int getFreePlaces() {

        int freePlaces = getAmountPlaces();

        for(int i = 0; i < room.size(); i++) {
            if(room.get(i).isReservedNow())
                freePlaces-=room.get(i).getMax_occupancy();

            freePlaces-=room.get(i).getCur_occupancy();
        }

        return freePlaces;
    }
}