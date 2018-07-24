package in.delbird.chemist.Controller;

/**
 * Created by deepak on 30/1/16.
 */
public class Validations {
    public boolean isValidUserName(String uname) {
        if (uname.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isValidEmail(String email) {
        boolean status = false;

        status = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (status == true) {
            return true;
        } else {
            return false;
        }
    }
}
