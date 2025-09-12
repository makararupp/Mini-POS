package makara.co.min_pos.models.response;

import lombok.Data;
import makara.co.min_pos.models.enums.EnumItemUnit;

import java.util.List;

@Data
public class CategoryResponse {
    private Integer id;
    private String categoryName;
    private String categoryCode;
    private String imagePath;
    private EnumItemUnit operator;
    private Double operationValue;
    private Long parentId;
    private List<CategoryResponse> children;

}
