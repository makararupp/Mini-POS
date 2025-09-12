package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.Sale;
import makara.co.min_pos.models.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale ,Long> {
    List<Sale> findByPaymentStatusAndSoldDate(PaymentStatus status, LocalDate date);
    boolean existsByIdAndIsDeletedFalseAndPaymentStatus(Long id, PaymentStatus paymentStatus);

    Optional<Sale> findByIdAndIsDeletedFalse(Long id);
    List<Sale> findAllByIsDeletedFalse();
    @Query("SELECT s FROM Sale s WHERE s.soldDate BETWEEN :startDate AND :endDate AND s.paymentStatus = :status")
    List<Sale> findSalesByDateRangeAndStatus(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") PaymentStatus status
    );
}
