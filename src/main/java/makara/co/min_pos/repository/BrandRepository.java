package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByIdAndIsDeletedFalse(Long id);
    List<Brand> findByIsDeletedIsFalseOrderByIdDesc();
    Page<Brand> findByIsDeletedIsFalseOrderByIdDesc(Pageable page);

}
