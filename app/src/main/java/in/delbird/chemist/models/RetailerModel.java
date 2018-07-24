package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahil on 3/4/16.
 */
public class RetailerModel implements Serializable{

    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("latlong")
    @Expose
    private String latlong;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price_discounted")
    @Expose
    private Long priceDiscounted;
    @SerializedName("retailer_id")
    @Expose
    private Long retailerId;
    @SerializedName("addressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("state")
    @Expose
    private String state;

    /**
     * @return The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The latlong
     */
    public String getLatlong() {
        return latlong;
    }

    /**
     * @param latlong The latlong
     */
    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

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

    /**
     * @return The addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1 The addressLine1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }
}
