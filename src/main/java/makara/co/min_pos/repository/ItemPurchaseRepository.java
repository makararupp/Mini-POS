package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.PurchaseItemUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemPurchaseRepository extends JpaRepository<PurchaseItemUnit,Long> {
    Optional<PurchaseItemUnit> findByIdAndIsDeletedFalse(Long id);
    List<PurchaseItemUnit> findByIsDeletedIsFalseOrderByIdDesc();
    Page<PurchaseItemUnit> findByIsDeletedIsFalseOrderByIdDesc(Pageable pageable);

}
