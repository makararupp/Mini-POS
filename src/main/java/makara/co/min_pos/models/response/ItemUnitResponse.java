package makara.co.min_pos.models.response;

import lombok.Data;
import makara.co.min_pos.models.enums.EnumItemUnit;

import java.util.List;

@Data
public class ItemUnitResponse {
    private Integer id;
    private String itemUnitCode;
    private String itemUnitName;
    private EnumItemUnit operator;
    private Double operationValue;
    private Long parentId;
    private List<Long> childId;
}
