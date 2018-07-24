package in.delbird.chemist.models;

import java.io.Serializable;

/**
 * Created by Dharmendra on 30/1/16.
 */
public class OtpResendResponceModel implements Serializable {

    private String code;
    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
