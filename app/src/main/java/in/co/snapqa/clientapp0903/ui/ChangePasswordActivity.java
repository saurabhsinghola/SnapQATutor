package in.co.snapqa.clientapp0903.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.subhrajyoti.passwordview.PasswordView;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.SignInController;
import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.ChangePasswordRequest;
import in.co.snapqa.clientapp0903.models.ChangePasswordResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChangePasswordActivity extends BaseActivity implements ApiControllerListener {

    PasswordView password, confirm;
    Button submit;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected String getActivityTitle() {
        return "Enter New Password";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        password = (PasswordView) findViewById(R.id.change_password_password);
        confirm = (PasswordView) findViewById(R.id.change_password_confirm);

        submit = (Button) findViewById(R.id.change_password_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final API service = retrofit.create(API.class);

        final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("", "");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOnPasswordChange();
                changePasswordRequest.setPassword(password.getText().toString());

                Call<ChangePasswordResponse> call = service.changePassword(changePasswordRequest);

                call.enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(Call<ChangePasswordResponse> call,
                            Response<ChangePasswordResponse> response) {
                        if (response.body().getMessage().equals("Successful")) {
                            Intent intent =
                                    new Intent(ChangePasswordActivity.this, SignInActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {

                    }
                });

            }
        });
    }

    private void callOnPasswordChange() {
        final ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("", "");
        changePasswordRequest.setPassword(password.getText().toString());

        new SignInController(this,this).callChangePassword(changePasswordRequest);

    }

    @Override
    public void onSuccessResult(Object response) {
        ChangePasswordResponse changePasswordResponse = (ChangePasswordResponse) response;
        if (changePasswordResponse.getMessage().equals("Successful")) {
            moveNextTo(SignInActivity.class);
        }


    }

    @Override
    public void onFailureResult() {

    }
}
