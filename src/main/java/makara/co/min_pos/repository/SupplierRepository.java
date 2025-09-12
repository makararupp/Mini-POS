package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByIdAndIsDeletedFalse(Long id);
    List<Supplier> findByIsDeletedIsFalseOrderByIdDesc();
    Page<Supplier> findByIsDeletedIsFalseOrderByIdDesc(Pageable pageable);

}
