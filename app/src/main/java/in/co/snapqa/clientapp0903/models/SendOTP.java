package in.co.snapqa.clientapp0903.models;

/**
 * Created by OLA on 29/03/17.
 */

public class SendOTP {

    String phone;

    public SendOTP(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
