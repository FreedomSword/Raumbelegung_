package SWT2.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_user")

@Data
@AllArgsConstructor
@NoArgsConstructor


public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private int uid;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String full_name;

    @Column (name = "password", nullable = false)
    private  String password;

    @Column (name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbL_user_role",
            joinColumns = @JoinColumn(name = "uid"),
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    private List<Role> roles = new ArrayList<>();

}
