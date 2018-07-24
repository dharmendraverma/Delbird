package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil on 2/4/16.
 */
public class ProductRetailerModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price_discounted")
    @Expose
    private Long priceDiscounted;
    @SerializedName("retailer_id")
    @Expose
    private Long retailerId;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The priceDiscounted
     */
    public Long getPriceDiscounted() {
        return priceDiscounted;
    }

    /**
     * @param priceDiscounted The price_discounted
     */
    public void setPriceDiscounted(Long priceDiscounted) {
        this.priceDiscounted = priceDiscounted;
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
}
