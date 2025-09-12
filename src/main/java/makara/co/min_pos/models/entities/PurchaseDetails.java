package makara.co.min_pos.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "purchase_details")
public class PurchaseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    @JsonIgnore
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private ItemProduct product;

    @Column(name = "product_name")
    private String productName;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "create_date")
    private LocalDate createdDate;
    private Integer unit;

    // Add this callback
    @PrePersist
    @PreUpdate
    public void prePersistOrUpdate() {
        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        if (unitPrice != null && unit != null) {
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(unit));
        } else {
            this.totalPrice = BigDecimal.ZERO;
        }
    }

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;


}
