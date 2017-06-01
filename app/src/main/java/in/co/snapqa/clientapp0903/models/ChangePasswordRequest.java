package in.co.snapqa.clientapp0903.models;

/**
 * Created by OLA on 13/04/17.
 */

public class ChangePasswordRequest {
    String password;

    public ChangePasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
