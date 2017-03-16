package in.co.snapqa.clientapp0903.interfaces;

import in.co.snapqa.clientapp0903.models.SignUpRequest;
import in.co.snapqa.clientapp0903.models.SignUpResponse;
import in.co.snapqa.clientapp0903.models.TokenRequest;
import in.co.snapqa.clientapp0903.models.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by OLA on 09/03/17.
 */

public interface API {

    @POST("signin")
    Call<TokenResponse> getTokenAccess(@Body TokenRequest tokenRequest);

    //Post request for Signing Up
    @POST("signup")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

}
