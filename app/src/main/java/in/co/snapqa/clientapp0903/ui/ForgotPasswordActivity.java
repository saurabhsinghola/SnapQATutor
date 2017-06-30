package in.co.snapqa.clientapp0903.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.SignInController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.ForgotPasswordRequest;
import in.co.snapqa.clientapp0903.models.ForgotPasswordResponse;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPasswordActivity extends BaseActivity implements ApiControllerListener {

    private static final String TAG = ForgotPasswordActivity.class.getSimpleName();
    Button submit;
    EditText phone;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected String getActivityTitle() {
        return "Forgot Password?";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        submit = (Button) findViewById(R.id.forgot_password_button);
        phone = (EditText) findViewById(R.id.forgto_password_phone);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callForgotPassword();

            }
        });
    }

    private void callForgotPassword() {
        final ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest("", "");
        forgotPasswordRequest.setPhone(phone.getText().toString());
        new SignInController(this, this).callForgotPassword(forgotPasswordRequest);

    }

    @Override
    public void onSuccessResult(Object response) {
        ForgotPasswordResponse passwordResponse = (ForgotPasswordResponse) response;
        if (passwordResponse.getMessage().equals("Successful")) {
            moveNextTo(ChangePasswordActivity.class);
        }
    }

    @Override
    public void onFailureResult() {
        Log.d(TAG, "Failed");
    }
}
