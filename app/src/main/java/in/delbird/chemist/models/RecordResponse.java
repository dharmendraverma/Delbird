package in.delbird.chemist.models;

import java.io.Serializable;

public class RecordResponse implements Serializable {

    private String Response;
    private String PWD;
    private String ErrNo;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getPWD() {
        return PWD;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

    public String getErrNo() {
        return ErrNo;
    }

    public void setErrNo(String errNo) {
        ErrNo = errNo;
    }
}
