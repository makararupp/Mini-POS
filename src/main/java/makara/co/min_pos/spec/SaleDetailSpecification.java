package makara.co.min_pos.spec;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import makara.co.min_pos.models.entities.Sale;
import makara.co.min_pos.models.entities.SaleDetail;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class SaleDetailSpecification implements Specification<SaleDetail> {
    private final SaleDetailFilter saleDetailFilter;

    @Override
    public Predicate toPredicate(Root<SaleDetail> saleDetail, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Join<SaleDetail, Sale> sale = saleDetail.join("sale");
        if(Objects.nonNull(saleDetailFilter.getStartDate())){
            criteriaBuilder.greaterThanOrEqualTo(sale.get("saleDate"),saleDetailFilter.getStartDate());
        }
        if(Objects.nonNull(saleDetailFilter.getEndDate())){
            criteriaBuilder.lessThanOrEqualTo(sale.get("soldDate"),saleDetailFilter.getEndDate());
        }
        Predicate predicate = criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        return predicate;
    }
}
