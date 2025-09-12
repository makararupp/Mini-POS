package makara.co.min_pos.models.entities;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product-sale")
public class ProductSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ItemProduct itemProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total-price")
    private Double totalPrice;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

    @PrePersist
    @PreUpdate
    private void calculateTotalPrice() {
        double discountValue = (discount != null) ? discount : 0.0;
        this.totalPrice = (unitPrice * qty) - discountValue;
    }

}
