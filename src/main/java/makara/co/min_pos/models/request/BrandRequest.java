package makara.co.min_pos.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BrandRequest {
    @NotBlank(message = "name is required!")
    @Length(min = 3, max = 150, message = "name is between 3 to 150 characters")
    String name;
}
