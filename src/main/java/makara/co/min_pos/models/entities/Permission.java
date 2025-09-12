package makara.co.min_pos.models.entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
