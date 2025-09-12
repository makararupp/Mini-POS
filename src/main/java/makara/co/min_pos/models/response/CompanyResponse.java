package makara.co.min_pos.models.response;

import lombok.Data;

@Data
public class CompanyResponse {
    private Integer id;
    private String companyLocalName;
    private String companyEngName;
    private String companyEmail;
    private String companyPhone;
    private String companyAddress;
    private String vatNumber;
    private String imagePath;
    private String image;
}
