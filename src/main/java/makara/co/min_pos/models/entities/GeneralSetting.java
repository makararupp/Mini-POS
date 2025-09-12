package makara.co.min_pos.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "general-setting")
public class GeneralSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sit_id")
    private Long id;
    @Column(name ="site_title")
    private String siteTitle;
    @Column(name = "site_logo")
    private String siteLogo;
    @Column(name = "site_phone")
    private String sitePhone;
    @Column(name = "site_address")
    private String siteAddress;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted = false;


}
