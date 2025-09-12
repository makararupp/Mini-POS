package makara.co.min_pos.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import makara.co.min_pos.models.enums.EnumItemProduct;

@Data
@Entity
@Table(name = "products")
public class ItemProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name",length = 100)
    private String productName;

    @Column(name = "product_code",length = 20)
    private String productCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type",length = 10)
    private EnumItemProduct productType;

    @Column(name = "barcode_symbology")
    private String  barcodeSymbology;

    @Column(name = "cost")
    private String cost;

    @Column(name = "price")
    private Double price;

    @Column(name = "qty")
    private Integer qty;

    @Column(name = "alert_qty")
    private Integer alertQty;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image")
    private String image;

    @Column(name = "isoverride_price", columnDefinition = "boolean default false")
    private Boolean isOverridePrice;

    @Column(name = "is_show_in_pos",columnDefinition = "boolean default true")
    private Boolean isShowInPos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category itemCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_unit_id")
    private ItemUnit itemUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_item_unit" )
    private PurchaseItemUnit purchaseItemUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_item_unit")
    private  ItemUnit saleItemUnit;

    @Column(name = "is_deleted",columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isDeleted  = false;


}
