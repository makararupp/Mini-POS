package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.ImportProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImportHistoriesRepository extends JpaRepository<ImportProductHistory, Long>,
        JpaSpecificationExecutor<ProductRepository> {

}
