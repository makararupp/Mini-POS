package makara.co.min_pos.models.response;

import lombok.Data;

@Data
public class GeneralSettingResponse {
    private Integer id;
    private String siteTitle;
    private String siteLogo;
    private String sitePhone;
    private String siteAddress;
}
