package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data

@AllArgsConstructor

@NoArgsConstructor

public class Raum {

    private long id;
    private String bezeichnung;
    private String typ;
    private int max_belegung;
    private int akt_belegung;
    private int gebaeudeId;


}
