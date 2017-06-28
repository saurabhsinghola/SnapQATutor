package in.co.snapqa.clientapp0903.controller;

import android.util.Log;
import android.widget.Toast;

import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.TokenRequest;
import in.co.snapqa.clientapp0903.models.TokenResponse;
import in.co.snapqa.clientapp0903.network.APIClient;
import in.co.snapqa.clientapp0903.ui.SignInActivity;
import in.co.snapqa.clientapp0903.utils.AppSharedPreferenceHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInController {
    private final ApiControllerListener apiControllerListener;
    private SignInActivity mContext;

    public SignInController(SignInActivity context,
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
//                    statusCode = response.code();
                    //String token = tokenResponse.getToken();

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
}
