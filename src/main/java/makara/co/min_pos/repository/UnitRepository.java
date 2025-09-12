package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.ItemUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<ItemUnit, Long> {
    Optional<ItemUnit> findByIdIsAndIsDeletedFalse(Long id);
    List<ItemUnit> findAllByParentIsNullAndIsDeletedFalseOrderByIdDesc();
    Page<ItemUnit> findAllByParentIsNullAndIsDeletedFalseOrderByIdDesc(Pageable pageable);

}
