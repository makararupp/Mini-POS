package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.ItemProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<ItemProduct, Long> ,
        JpaSpecificationExecutor<ItemProduct> {

    Optional<ItemProduct> findByIdAndIsDeletedFalse(Long id);
    List<ItemProduct> findByIsDeletedIsFalseOrderByIdDesc();
    Page<ItemProduct> findByIsDeletedIsFalseOrderByIdDesc(Pageable pageable);
    List<ItemProduct> findByIdInAndIsDeletedFalse(Set<Long> ids);

}
