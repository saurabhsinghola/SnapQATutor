package in.co.snapqa.clientapp0903.models;

/**
 * Created by OLA on 13/04/17.
 */

public class ChangePasswordRequest {
    String phone;
    String password;

    public ChangePasswordRequest(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}