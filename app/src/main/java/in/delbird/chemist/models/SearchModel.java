package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchModel implements Serializable {


    @SerializedName("retailerFound")
    @Expose
    private Boolean retailerFound;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("retailer")
    @Expose
    private RetailerModel retailer;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("price")
    @Expose
    private Long price;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("availability")
    @Expose
    private Boolean availability;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("retailer_id")
    @Expose
    private Long retailerId;
    /**
     * @return The retailerFound
     */
    public Boolean getRetailerFound() {
        return retailerFound;
    }

    /**
     * @param retailerFound The retailerFound
     */
    public void setRetailerFound(Boolean retailerFound) {
        this.retailerFound = retailerFound;
    }

    /**
     * @return The quantity
     */
    public String getQuantity() {
        return quantity;
    }



    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * @return The retailer
     */
    public RetailerModel getRetailer() {
        return retailer;
    }

    /**
     * @param retailer The retailer
     */
    public void setRetailer(RetailerModel retailer) {
        this.retailer = retailer;
    }

    /**
     * @return The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * @return The price
     */
    public Long getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(Long price) {
        this.price = price;
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
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The availability
     */
    public Boolean getAvailability() {
        return availability;
    }

    /**
     * @param availability The availability
     */
    public void setAvailability(Boolean availability) {
        this.availability = availability;
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

    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }
}

