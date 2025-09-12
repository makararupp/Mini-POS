package makara.co.min_pos.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exchange_rate")
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "from_currency")
    private String fromCurrency;
    @Column(name = "to_currency")
    private String toCurrency;
    @Column(name = "exchange_rate")
    private Double exchangeRate;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted = false;

}
