package in.co.snapqa.clientapp0903.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OLA on 13/04/17.
 */

public class ChangePasswordResponse {

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("user")
    @Expose
    String user;

    public ChangePasswordResponse(String message, String user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
