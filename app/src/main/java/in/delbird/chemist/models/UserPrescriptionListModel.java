package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahil on 10/4/16.
 */
public class UserPrescriptionListModel implements Serializable {

    @SerializedName("dose_noon")
    @Expose
    private Long doseNoon;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("dose_night")
    @Expose
    private Long doseNight;
    @SerializedName("dose_morning")
    @Expose
    private Long doseMorning;
    @SerializedName("item_id")
    @Expose
    private Long itemId;
    @SerializedName("prescriptionMaster_id")
    @Expose
    private Long prescriptionMasterId;
    @SerializedName("product_id")
    @Expose
    private Long productId;
    @SerializedName("duration_type")
    @Expose
    private String durationType;
    @SerializedName("duration_days")
    @Expose
    private Long durationDays;
    @SerializedName("food_pref")
    @Expose
    private String foodPref;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_name")
    @Expose
    private String productName;

    /**
     * @return The doseNoon
     */
    public Long getDoseNoon() {
        return doseNoon;
    }

    /**
     * @param doseNoon The dose_noon
     */
    public void setDoseNoon(Long doseNoon) {
        this.doseNoon = doseNoon;
    }

    /**
     * @return The instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions The instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * @return The doseNight
     */
    public Long getDoseNight() {
        return doseNight;
    }

    /**
     * @param doseNight The dose_night
     */
    public void setDoseNight(Long doseNight) {
        this.doseNight = doseNight;
    }

    /**
     * @return The doseMorning
     */
    public Long getDoseMorning() {
        return doseMorning;
    }

    /**
     * @param doseMorning The dose_morning
     */
    public void setDoseMorning(Long doseMorning) {
        this.doseMorning = doseMorning;
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
     * @return The prescriptionMasterId
     */
    public Long getPrescriptionMasterId() {
        return prescriptionMasterId;
    }

    /**
     * @param prescriptionMasterId The prescriptionMaster_id
     */
    public void setPrescriptionMasterId(Long prescriptionMasterId) {
        this.prescriptionMasterId = prescriptionMasterId;
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
     * @return The durationType
     */
    public String getDurationType() {
        return durationType;
    }

    /**
     * @param durationType The duration_type
     */
    public void setDurationType(String durationType) {
        this.durationType = durationType;
    }

    /**
     * @return The durationDays
     */
    public Long getDurationDays() {
        return durationDays;
    }

    /**
     * @param durationDays The duration_days
     */
    public void setDurationDays(Long durationDays) {
        this.durationDays = durationDays;
    }

    /**
     * @return The foodPref
     */
    public String getFoodPref() {
        return foodPref;
    }

    /**
     * @param foodPref The food_pref
     */
    public void setFoodPref(String foodPref) {
        this.foodPref = foodPref;
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
}
