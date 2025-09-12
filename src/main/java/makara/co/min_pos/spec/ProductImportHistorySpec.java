package makara.co.min_pos.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import makara.co.min_pos.models.entities.ImportProductHistory;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ProductImportHistorySpec implements Specification<ImportProductHistory> {
    private ProductImportHistoryFilter filter;

    @Override
    public Predicate toPredicate(Root<ImportProductHistory> importProductHistory,
                                 CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull( filter.getStartDate())) {
            cb.greaterThanOrEqualTo(importProductHistory.get("dateImport"),  filter.getStartDate());
        }
        if(Objects.nonNull(filter.getEndDate())) {
            cb.lessThanOrEqualTo(importProductHistory.get("dateImport"),filter.getEndDate());
        }
        Predicate predicate = cb.and(predicates.toArray(Predicate[]::new));
        return predicate;
    }

}
