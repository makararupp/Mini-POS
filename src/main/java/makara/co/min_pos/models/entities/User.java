package makara.co.min_pos.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNoExpired;
    private boolean enable;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

}
