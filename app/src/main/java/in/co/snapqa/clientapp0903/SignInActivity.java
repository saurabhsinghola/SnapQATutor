package in.co.snapqa.clientapp0903;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.subhrajyoti.passwordview.PasswordView;

import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.SignInController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;

public class SignInActivity extends BaseActivity implements ApiControllerListener {


    private static final String TAG = "SignIn";
    Button signInButton;
    EditText phone;
    PasswordView password;
    Drawable drawable;
    TextView signUpText, forgotPassword;
    Boolean doubleBackToExitPressedOnce = false;
    String pass;
    String phoneNumber;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(startMain);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.go_back_again, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        signInButton = (Button) findViewById(R.id.signinbutton);
        signUpText = (TextView) findViewById(R.id.signUpText);
        password = (PasswordView) findViewById(R.id.signinpassword);
        phone = (EditText) findViewById(R.id.signinphone);
        forgotPassword = (TextView) findViewById(R.id.sign_in_forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveNextTo(ForgotPasswordActivity.class);
            }
        });

        drawable = getResources().getDrawable(R.drawable.user);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sign In");
        }


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSignInFunction();
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveNextTo(SignUpActivity.class);
            }
        });
    }

    private void callSignInFunction() {
        pass = password.getText().toString();
        phoneNumber = phone.getText().toString();
        if (phoneNumber.length() < 10 || pass.length() < 4) {
            Toast.makeText(this, "Invalid Credential", Toast.LENGTH_LONG).show();
            return;
        }
        new SignInController(this, this, phoneNumber, pass).callSignInApi();
    }

    @Override
    public void onSuccessResult(Object response) {
        moveNextTo(MainActivity.class);
    }

    @Override
    public void onFailureResult() {

    }
}
