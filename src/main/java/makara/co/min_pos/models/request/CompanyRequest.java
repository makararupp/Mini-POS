package makara.co.min_pos.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CompanyRequest {
    @NotBlank(message = "company local name is required!")
    @Length(min =4, max = 250, message = "company local name between 4 to 250 characters")
    private String companyLocalName;

    @NotBlank(message = "company english name is required!")
    @Length(min =4, max = 200, message = "company english between 4 to 200 characters")
    private String companyEngName;

    @NotBlank(message = "company email name is required!")
    @Length(min =4,max = 100,message = "email between 4 to 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email address")
    private String companyEmail;

    @NotBlank(message = "company phone is required!")
    @Length(min=9, max = 12 , message = "phone between 9 to 12 characters")
    @Pattern(regexp = "^((\\\\+855)|0)?[1-9][0-9]{7,8}$",message = "Invalid phone number")
    private String companyPhone;

    @NotBlank(message = "Address is required!")
    @Length(min = 8, max = 200, message = "Address between 12 to 200 characters")
    private String companyAddress;

    @NotBlank(message = "vat number is required!")
    @Length(min =6, max = 12, message = "vat number is between 6 to 12 characters")
    @Pattern(regexp = "^A\\d{6}$", message = "VAT Number must start with 'A' followed by 9 digits")
    private String vatNumber;

    private String imagePath;
    private String image;

}
