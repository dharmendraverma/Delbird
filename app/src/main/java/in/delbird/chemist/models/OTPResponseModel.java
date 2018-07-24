package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OTPResponseModel implements Serializable {

    @SerializedName("user_id")
    @Expose
    private Long userId;
    @SerializedName("newUser")
    @Expose
    private Boolean newUser;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * @return The userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId The user_id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return The newUser
     */
    public Boolean getNewUser() {
        return newUser;
    }

    /**
     * @param newUser The newUser
     */
    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }

    /**
     * @return The errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode The errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return The otp
     */
    public String getOtp() {
        return otp;
    }

    /**
     * @param otp The otp
     */
    public void setOtp(String otp) {
        this.otp = otp;
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
