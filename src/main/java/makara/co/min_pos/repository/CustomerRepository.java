package makara.co.min_pos.repository;
import makara.co.min_pos.models.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
     Optional<Customer> findByIdAndIsDeletedFalse(Long id);
     List<Customer> findByIsDeletedIsFalseOrderByIdDesc();
     Page<Customer> findByIsDeletedIsFalseOrderByIdDesc(Pageable pageable);
}
