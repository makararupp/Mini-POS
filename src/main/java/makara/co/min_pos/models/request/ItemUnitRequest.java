package makara.co.min_pos.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import makara.co.min_pos.models.enums.EnumItemUnit;
import org.hibernate.validator.constraints.Length;

@Data
public class ItemUnitRequest {

    @NotBlank(message = "item unit code is required")
    @Length(min = 4, max = 80, message = "code is between 4 to 80 characters")
    private String itemUnitCode;

    @NotBlank(message = "item unit name is required")
    @Length(min=4, max = 150, message = "name is between 4 to 150 characters")
    private String itemUnitName;

    @NotNull(message = "operation is required")
    private EnumItemUnit operator;

    @NotNull(message = "operation value is required")
    private Double operationValue;

    private String parentId;


}
