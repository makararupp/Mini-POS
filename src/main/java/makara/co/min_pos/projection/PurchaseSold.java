package makara.co.min_pos.projection;

public interface PurchaseSold {
    Long getProductId();
    String getProductName();
    Double getUnit(); // Total units purchased
    Double getTotalAmount();
}
