package makara.co.min_pos.service;
import makara.co.min_pos.models.entities.Sale;
import makara.co.min_pos.models.enums.PaymentStatus;
import makara.co.min_pos.models.response.SaleResponse;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {
     Sale create(Sale sale);
     Sale getById(Long id);
     List<Sale> getSalesByStatusAndDate(PaymentStatus status, LocalDate date);
     List<SaleResponse> getAllSold();
     List<Sale> getSalesByStatusAndDate(LocalDate startDate,
                                        LocalDate endDate, PaymentStatus status);
     Sale updatePayment(Long id, PaymentStatus status);

     Sale delete(Long id);

     Sale cancelSale(Long saleId);

}
