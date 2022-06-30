package SWT2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@Table(name = "tbl_role")
@AllArgsConstructor
@NoArgsConstructor

public class Role {

    @Id
    @GeneratedValue
    @Column(name = "roleid")

    private int roleid;

    @Column(name = "name")
    private String name;


}
