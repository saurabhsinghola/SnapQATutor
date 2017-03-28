package in.co.snapqa.clientapp0903.interfaces;

import java.util.List;

import in.co.snapqa.clientapp0903.models.AuthRequest;
import in.co.snapqa.clientapp0903.models.NewDealFragmentResponse;
import in.co.snapqa.clientapp0903.models.NewDealResponses;
import in.co.snapqa.clientapp0903.models.SignUpRequest;
import in.co.snapqa.clientapp0903.models.SignUpResponse;
import in.co.snapqa.clientapp0903.models.SubjectAddRequest;
import in.co.snapqa.clientapp0903.models.SubjectAddResponse;
import in.co.snapqa.clientapp0903.models.TokenRequest;
import in.co.snapqa.clientapp0903.models.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by OLA on 09/03/17.
 */

public interface API {

    @POST("users/signin")
    Call<TokenResponse> getTokenAccess(@Body TokenRequest tokenRequest);

    //Post request for Signing Up
    @POST("users/signup")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @POST("deals/viewDealsUpComing")
    Call<NewDealResponses> newDeals(@Body AuthRequest authRequest);

    @POST("users/addSubjects")
    Call<SubjectAddResponse> addSubject(@Body SubjectAddRequest subjectAddRequest);

}
