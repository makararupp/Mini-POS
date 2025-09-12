package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByIdAndIsDeletedFalse(Long id);
    List<Company> findByIsDeletedIsFalseOrderByIdDesc();
    Page<Company> findByIsDeletedIsFalseOrderByIdDesc(Pageable pageable);

}
