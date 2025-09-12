package makara.co.min_pos.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "purchase_item_unit")
public class PurchaseItemUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_product_id")
    private ItemProduct itemProduct;

    @Column(name = "unit_name")
    private String unitName;

    @Column(name = "conversion_factor")
    private Double conversionFactor;

    @Column(name = "cost_per_unit")
    private Double costPerUnit;

    @Column(name = "pur-date")
    private LocalDate purchaseDate;

    @Column(name = "is_deleted",columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isDeleted = false;

}
