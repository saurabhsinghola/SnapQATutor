package in.co.snapqa.clientapp0903.controller;

import android.util.Log;
import android.widget.Toast;

import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.OTPResponse;
import in.co.snapqa.clientapp0903.models.SendOTP;
import in.co.snapqa.clientapp0903.models.SignUpRequest;
import in.co.snapqa.clientapp0903.models.SignUpResponse;
import in.co.snapqa.clientapp0903.models.TokenRequest;
import in.co.snapqa.clientapp0903.models.TokenResponse;
import in.co.snapqa.clientapp0903.network.APIClient;
import in.co.snapqa.clientapp0903.utils.AppSharedPreferenceHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInController {
    private final ApiControllerListener apiControllerListener;
    private BaseActivity mContext;

    public SignInController(BaseActivity context,
            ApiControllerListener apiControllerListener) {
        mContext = context;
        this.apiControllerListener = apiControllerListener;

    }

    public void callSignInApi(final String phoneNumber, final String pass) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setPhone(phoneNumber);
        tokenRequest.setPassword(pass);
        mContext.showMessageDialog("Just a sec!", "Logging you in");
        APIClient.getClient().getTokenAccess(tokenRequest).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                mContext.dismissDialog();
                if (response.isSuccessful()) {
                    TokenResponse tokenResponse = response.body();
                    switch (tokenResponse.message) {
                        case "Successful":
                            Toast.makeText(mContext, "Success",
                                    Toast.LENGTH_LONG).show();
                            String token = response.body().getToken();
                            //For Saving Token
                            AppSharedPreferenceHelper.set(AppSharedPreferenceHelper.KEY, token);
                            AppSharedPreferenceHelper.set(AppSharedPreferenceHelper.PHONE,
                                    phoneNumber);
                            // OTP Verification
                            apiControllerListener.onSuccessResult(tokenResponse);

                            break;
                        case "Unsuccessful":
                            Toast.makeText(mContext, "Incorrect credentials",
                                    Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Log.d("else: ", "" + response.body().getMessage());
                            Log.d("phone: ", "" + phoneNumber);
                            Log.d("password: ", "" + pass);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Toast.makeText(mContext, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callOtpVerify(String phone) {
        SendOTP sendOTPObj = new SendOTP("");
        sendOTPObj.setPhone(phone);
        APIClient.getClient().otpVerified(sendOTPObj).enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call,
                    retrofit2.Response<OTPResponse> response) {
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

    public void callSignUpApi(SignUpRequest signUpRequest) {

        APIClient.getClient().signUp(signUpRequest).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                apiControllerListener.onSuccessResult(response);
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

            }
        });

    }
}
