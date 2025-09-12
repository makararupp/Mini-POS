package makara.co.min_pos.models.entities;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "brand_name")
    private String name;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

}
