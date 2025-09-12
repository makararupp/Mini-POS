package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.enums.PurchaseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>,
        JpaSpecificationExecutor<Purchase> {
    List<Purchase> findAllBySupplierIdAndIsDeletedFalse(Long suplierId);
    List<Purchase> findBySupplierIsDeletedFalseOrderByIdDesc();
    Optional<Purchase> findByIdAndIsDeletedFalse(Long id);
    Page<Purchase> findBySupplierIsDeletedFalseOrderByIdDesc(Pageable pageable);
    @Query("SELECT p FROM Purchase p " +
            "WHERE p.status = :status AND p.purchaseDate BETWEEN :startDate AND :endDate  AND p.isDeleted = false")
    List<Purchase> findPurchasesByStatusAndDateRange(
            @Param("status") PurchaseStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
