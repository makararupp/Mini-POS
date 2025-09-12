package makara.co.min_pos.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Table(name = "roles")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissions;

}
