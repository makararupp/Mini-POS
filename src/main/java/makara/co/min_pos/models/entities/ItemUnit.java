package makara.co.min_pos.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import makara.co.min_pos.models.enums.EnumItemUnit;

import java.util.List;

@Data
@Entity
@Table(name = "units")
public class ItemUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_unit_code",length = 10)
    private String itemUnitCode;

    @Column(name = "item_unit_name", length = 150)
    private String itemUnitName;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator",length = 10)
    private EnumItemUnit operator;

    @Column(name = "operation_value")
    private Double operationValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private ItemUnit parent;

    @OneToMany(mappedBy = "parent",cascade =CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<ItemUnit> chidrent;

    @Column(name = "is_deleted",columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isDeleted = false;


}
