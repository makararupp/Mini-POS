package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSaleRepository extends JpaRepository<ProductSale, Long> {
    ProductSale findByItemProductIdAndSaleId(Long productId, Long saleId);
    List<ProductSale> findByItemProductId(Long productSaleId);

    Optional<ProductSale> findByIdAndIsDeletedFalse(Long productSaleId);

}
