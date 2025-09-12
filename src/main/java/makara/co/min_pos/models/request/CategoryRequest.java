package makara.co.min_pos.models.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import makara.co.min_pos.models.enums.EnumItemUnit;
import org.hibernate.validator.constraints.Length;

@Data
public class CategoryRequest {
    @NotBlank(message = "Name is required!")
    @Length(min =4, max = 150, message = "Name is between 4 to 150 characters")
    private String categoryName;

    @NotBlank(message = "Name is required!")
    @Length(min =4, max = 10, message = "Code is between 4 to 150 characters")
    private String categoryCode;

    @NotNull(message = "Operator is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "operator", length = 10)
    private EnumItemUnit operator;

    @NotNull(message = "Operation value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Operation value must be greater than 0")
    @Column(name = "operation_value")
    private Double operationValue;

    private String imagePath;
    private Long parentId;

}
