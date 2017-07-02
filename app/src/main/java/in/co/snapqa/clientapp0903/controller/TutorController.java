package in.co.snapqa.clientapp0903.controller;

import android.util.Log;
import android.widget.Toast;

import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.AuthRequest;
import in.co.snapqa.clientapp0903.models.OTPResponse;
import in.co.snapqa.clientapp0903.models.SendOTP;
import in.co.snapqa.clientapp0903.models.UserProfileResponse;
import in.co.snapqa.clientapp0903.network.APIClient;
import in.co.snapqa.clientapp0903.utils.AppSharedPreferenceHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TutorController {
    private final ApiControllerListener apiControllerListener;
    private BaseActivity mContext;

    public TutorController(BaseActivity context,
            ApiControllerListener apiControllerListener) {
        mContext = context;
        this.apiControllerListener = apiControllerListener;

    }


    public void callOtpVerify(String phone) {
        SendOTP sendOTPObj = new SendOTP("");
        sendOTPObj.setPhone(phone);
        APIClient.getClient().otpVerified(sendOTPObj).enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call,
                    Response<OTPResponse> response) {
                String message = response.body().getMessage();
                if (message.equals("Successful")) {
                    Toast.makeText(mContext, "Successfully Verified",
                            Toast.LENGTH_LONG).show();
                    mContext.dismissDialog();
                    apiControllerListener.onSuccessResult(response);
                }
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {
                mContext.dismissDialog();
            }
        });

    }

    public void callUserProfile() {
        String token =
                AppSharedPreferenceHelper.getString(AppSharedPreferenceHelper.KEY, "notPresent");
        AuthRequest authRequest = new AuthRequest();
        authRequest.setToken(token);

        APIClient.getClient().userProfile(authRequest).enqueue(new Callback<UserProfileResponse>() {
            @Override
            public void onResponse(Call<UserProfileResponse> call,
                    Response<UserProfileResponse> response) {
                apiControllerListener.onSuccessResult(response);

            }

            @Override
            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                Log.d("error in response:  ", "  " + t.getMessage());
            }
        });
    }
}
