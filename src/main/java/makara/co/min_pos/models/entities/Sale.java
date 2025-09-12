package makara.co.min_pos.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import makara.co.min_pos.models.enums.PaymentStatus;

import java.time.LocalDate;

@Data
@Table(name = "sales")
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @Column(name = "sold_date")
    private LocalDate soldDate;

    @Column(name = "amount")
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status",length = 4)
    private PaymentStatus paymentStatus;

    @Column(name = "is_deleted",columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isDeleted  = false;

    @Column(name = "total_qty")
    private Integer totalQty;


}
