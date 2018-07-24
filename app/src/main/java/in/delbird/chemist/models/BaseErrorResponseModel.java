package in.delbird.chemist.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseErrorResponseModel implements Serializable {

    @SerializedName("errorCode")
    @Expose
    private String errorCode;
    @SerializedName("status")
    @Expose
    private String status;


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
