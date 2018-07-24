package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahil on 3/4/16.
 */
public class InvoiceListItemModel implements Serializable {

    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("invoiceMaster_id")
    @Expose
    private Long invoiceMasterId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("payment_amount")
    @Expose
    private Long paymentAmount;
    @SerializedName("addressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("status")
    @Expose
    private String status;

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
     * @return The paymentMode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * @param paymentMode The payment_mode
     */
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

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
     * @return The paymentAmount
     */
    public Long getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * @param paymentAmount The payment_amount
     */
    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
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
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
