package in.co.snapqa.clientapp0903.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OLA on 09/03/17.
 */

public class TokenResponse {
    @SerializedName("message")
    @Expose
    public String message;

    public String getMessage() {
        return message;
    }

    @SerializedName("token")
    @Expose
    public String token;

    public String getToken() {
        return token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenResponse(String message, String token) {

        this.message = message;
        this.token = token;
    }
}
