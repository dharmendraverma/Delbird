package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahil on 3/4/16.
 */
public class InvoiceDetailItemModel implements Serializable{

    @SerializedName("invoiceMaster_id")
    @Expose
    private Long invoiceMasterId;
    @SerializedName("quantity")
    @Expose
    private Long quantity;
    @SerializedName("item_id")
    @Expose
    private Long itemId;
    @SerializedName("prodyct_price_discounted")
    @Expose
    private String prodyctPriceDiscounted;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("retailer_id")
    @Expose
    private Long retailerId;
    @SerializedName("accepted")
    @Expose
    private Boolean accepted;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_name")
    @Expose
    private String productName;

    @SerializedName("image_url")
    @Expose
    private String image_url;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * @return The invoiceMasterId
     */
    public Long getInvoiceMasterId() {
        return invoiceMasterId;
    }

    /**
     * @param invoiceMasterId The invoiceMaster_id
     */
    public void setInvoiceMasterId(Long invoiceMasterId) {
        this.invoiceMasterId = invoiceMasterId;
    }

    /**
     * @return The quantity
     */
    public Long getQuantity() {
        return quantity;
    }

    /**
     * @param quantity The quantity
     */
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    /**
     * @return The itemId
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * @param itemId The item_id
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return The prodyctPriceDiscounted
     */
    public String getProdyctPriceDiscounted() {
        return prodyctPriceDiscounted;
    }

    /**
     * @param prodyctPriceDiscounted The prodyct_price_discounted
     */
    public void setProdyctPriceDiscounted(String prodyctPriceDiscounted) {
        this.prodyctPriceDiscounted = prodyctPriceDiscounted;
    }

    /**
     * @return The productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId The product_id
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return The retailerId
     */
    public Long getRetailerId() {
        return retailerId;
    }

    /**
     * @param retailerId The retailer_id
     */
    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }

    /**
     * @return The accepted
     */
    public Boolean getAccepted() {
        return accepted;
    }

    /**
     * @param accepted The accepted
     */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * @return The productPrice
     */
    public String getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice The product_price
     */
    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName The product_name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
