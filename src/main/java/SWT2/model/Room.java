package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Column(nullable = true)
    private String photo;


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

    @Transient
    public String getPhotosImagePath() {
        if (photo == null || rid == 0) return null;

        return "/room-photos/" + name + "/" + photo;
    }

    @Transient
    public boolean isReservedNow() {

        String date = LocalDate.of(2022,06,24).toString();
        String hour = LocalTime.now().getHour() +":00:00";

        boolean reserved = false;

        for(int i = 0; i < reservations.size(); i++) {
            if(reservations.get(i).getDate().equals(date) && reservations.get(i).getTime().equals(hour)) {
                    reserved = true;
            }
        }
        return reserved;
    }

    @Transient
    public String reservedString() {
        if (isReservedNow()) return "Reserviert";
        else return "Frei";
    }





}
