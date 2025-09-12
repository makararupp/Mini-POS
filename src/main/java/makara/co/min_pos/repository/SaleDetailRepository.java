package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleDetailRepository extends JpaRepository<SaleDetail,Long>,
        JpaSpecificationExecutor<SaleDetail> {
    List<SaleDetail> findBySaleId(Long saleId);

}
