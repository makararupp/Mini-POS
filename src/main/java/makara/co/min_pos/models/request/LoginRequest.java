package makara.co.min_pos.models.request;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request payload")
public class LoginRequest {
    @NotBlank
    @Schema(description = "Email address from user")
    private String username;

   // @Schema(example = "password123")
    @NotBlank
    private String password;
}
