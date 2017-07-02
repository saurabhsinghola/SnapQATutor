package in.co.snapqa.clientapp0903.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.subhrajyoti.passwordview.PasswordView;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.SignInController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.ChangePasswordRequest;
import in.co.snapqa.clientapp0903.models.ChangePasswordResponse;
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOnPasswordChange();
            }
        });
    }

    private void callOnPasswordChange() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("", "");
        changePasswordRequest.setPassword(password.getText().toString());
        new SignInController(this, this).callChangePassword(changePasswordRequest);

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
