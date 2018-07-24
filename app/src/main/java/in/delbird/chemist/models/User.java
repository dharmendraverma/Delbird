package in.delbird.chemist.models;

/**
 * Created by deepak on 1/2/16.
 */
public class User {

    Long user_id;


    String name;

    String email;

    String mobile_no;

    String gcm_id;

    public User() {
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return this.mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getGcm_id() {
        return this.gcm_id;
    }

    public void setGcm_id(String gcm_id) {
        this.gcm_id = gcm_id;
    }
}
