package in.co.snapqa.clientapp0903.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.subhrajyoti.passwordview.PasswordView;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.SignInController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.SignUpRequest;
import in.co.snapqa.clientapp0903.models.SignUpResponse;
import in.co.snapqa.clientapp0903.utils.AppSharedPreferenceHelper;

public class SignUpActivity extends BaseActivity implements ApiControllerListener {

    private static final String TAG = SignUpActivity.class.getName();
    EditText signUpPhone, signUpPassword, signUpEmail, signUpName, signUpWhatsapp;
    Button signUpButton;
    PasswordView signupPassword;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String namePattern = "[a-zA-Z ]+[ ]+[a-zA-Z]+[a-zA-Z ]";

    @Override
    protected String getActivityTitle() {
        return "Sign Up";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpButton = (Button) findViewById(R.id.sugnupbutton);
        signUpEmail = (EditText) findViewById(R.id.signupemail);
        signUpName = (EditText) findViewById(R.id.signupname);
        //signUpPassword = (EditText) findViewById(R.id.signuppassword);
        signUpPhone = (EditText) findViewById(R.id.signupphone);
        signupPassword = (PasswordView) findViewById(R.id.signuppassword);
        signUpWhatsapp = (EditText) findViewById(R.id.signupwhatsapp);

        AppSharedPreferenceHelper.set(AppSharedPreferenceHelper.PHONE,
                signUpPhone.getText().toString());


//        final Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(getString(R.string.api_url))
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

//        final API service = retrofit.create(API.class);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpAction();


//                final SignUpRequest signUpRequest = new SignUpRequest("", "", "", "", "");
//
//                signUpRequest.setPhone(signUpPhone.getText().toString());
//                signUpRequest.setPassword(signupPassword.getText().toString());
//                signUpRequest.setEmail(signUpEmail.getText().toString());
//                signUpRequest.setName(signUpName.getText().toString());
//                signUpRequest.setWhatsapp(signUpWhatsapp.getText().toString());

//                Call<SignUpResponse> signUpResponseCall = service.signUp(signUpRequest);
//
//                int phoneLength, passwordLength, emailLength, nameLength;
//                phoneLength = signUpPhone.getText().length();
//                passwordLength = signupPassword.getText().length();
//                emailLength = signUpEmail.getText().length();
//                nameLength = signUpName.getText().length();
//

//                if (phoneLength == 10) {
//                    if (passwordLength > 8) {
//                        if (signUpEmail.getText().toString().matches(emailPattern)
//                                && emailLength > 0) {
//                            if (signUpName.getText().toString().matches(namePattern)
//                                    && nameLength > 0) {
//
//                                progressDialog =
//                                        ProgressDialog.show(SignUpActivity.this, "Just a sec!",
//                                                "Signing you up!", true);
//                                signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
//
//
//                                    @Override
//                                    public void onResponse(Call<SignUpResponse> call,
//                                            Response<SignUpResponse> response) {
//                                        SignUpResponse signUpResponse = response.body();
//                                        int statusCode = response.code();
//                                        Log.d("response:  ", "" + response.body().getToken());
//                                        Log.d("Signup response  : ",
//                                                "" + signUpResponse.toString());
//                                        if (signUpResponse.message.equals("Successful")) {
//                                            Toast.makeText(SignUpActivity.this, "Success",
//                                                    Toast.LENGTH_LONG).show();
//                                            Log.d("status code: ", "" + statusCode);
//                                            AppSharedPreferenceHelper.set(
//                                                    AppSharedPreferenceHelper.PHONE,
//                                                    signUpPhone.getText().toString());
//
//
//                                            AppSharedPreferenceHelper.set(
//                                                    AppSharedPreferenceHelper.KEY,
//                                                    signUpResponse.getToken());
//                                            progressDialog.dismiss();
//                                            Toast.makeText(SignUpActivity.this, "Successful !!",
//                                                    Toast.LENGTH_LONG).show();
//
//                                            //OTP Verification
//                                            Intent otpVerification = new Intent(SignUpActivity.this,
//                                                    VerifyOTPActivity.class);
//                                            startActivity(otpVerification);
//
//
//                                        } else if (signUpResponse.message.equals("Unsuccessful")) {
//                                            progressDialog.dismiss();
//                                            Toast.makeText(SignUpActivity.this, "already existing",
//                                                    Toast.LENGTH_LONG).show();
//                                            Log.d("status code: ", "" + statusCode);
//
//                                            AppSharedPreferenceHelper.set(
//                                                    AppSharedPreferenceHelper.PHONE,
//                                                    signUpPhone.getText().toString());
//
//
//                                        } else {
//                                            progressDialog.dismiss();
//                                        }
//
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<SignUpResponse> call, Throwable t) {
//
//                                        Toast.makeText(SignUpActivity.this, "Failed",
//                                                Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                            } else {
//
//                                Toast.makeText(SignUpActivity.this, "Enter Full Name",
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        } else {
//
//                            Toast.makeText(SignUpActivity.this, "Invalid Email",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    } else {
//
//                        Toast.makeText(SignUpActivity.this, "Make Strong Password",
//                                Toast.LENGTH_LONG).show();
//                    }
//                } else {

//                    Toast.makeText(SignUpActivity.this, "Incorrect Phone Number",
//                            Toast.LENGTH_LONG).show();
//                }


            }
        });

    }

    private void signUpAction() {

        final SignUpRequest signUpRequest = new SignUpRequest("", "", "", "", "");

        signUpRequest.setPhone(signUpPhone.getText().toString());
        signUpRequest.setPassword(signupPassword.getText().toString());
        signUpRequest.setEmail(signUpEmail.getText().toString());
        signUpRequest.setName(signUpName.getText().toString());
        signUpRequest.setWhatsapp(signUpWhatsapp.getText().toString());
        if (validateEntry()) {
            new SignInController(this, this).callSignUpApi(signUpRequest);
        }

    }

    private boolean validateEntry() {
        int phoneLength, passwordLength, emailLength, nameLength;
        phoneLength = signUpPhone.getText().length();
        passwordLength = signupPassword.getText().length();
        emailLength = signUpEmail.getText().length();
        nameLength = signUpName.getText().length();
        if (phoneLength != 10) {
            Toast.makeText(SignUpActivity.this, "Incorrect Phone Number",
                    Toast.LENGTH_LONG).show();
            signUpPhone.setError("Incorrect Phone Number");
            signUpPhone.requestFocus();
            return false;
        }
        if (passwordLength <= 8) {
            Toast.makeText(SignUpActivity.this, "Make Strong Password",
                    Toast.LENGTH_LONG).show();
            signupPassword.setError("Make Strong Password");
            signupPassword.requestFocus();
            return false;
        }

        if (!signUpEmail.getText().toString().matches(emailPattern)
                || emailLength <= 0) {

            Toast.makeText(SignUpActivity.this, "Invalid Email",
                    Toast.LENGTH_LONG).show();
            signUpEmail.setError("Invalid Email");
            signUpEmail.requestFocus();
            return false;
        }
        if (!signUpName.getText().toString().matches(namePattern)
                || nameLength <= 0) {
            Toast.makeText(SignUpActivity.this, "Enter Full Name",
                    Toast.LENGTH_LONG).show();
            signUpName.setError("Enter Full Name");
            signUpName.requestFocus();
            return false;
        }
        return true;


    }

    @Override
    public void onSuccessResult(Object response) {

        SignUpResponse signUpResponse = (SignUpResponse) response;
        Log.d("response:  ", "" + signUpResponse.getToken());
        Log.d("Signup response  : ",
                "" + signUpResponse.toString());
        if (signUpResponse.message.equals("Successful")) {
            Toast.makeText(SignUpActivity.this, "Success",
                    Toast.LENGTH_LONG).show();
            AppSharedPreferenceHelper.set(
                    AppSharedPreferenceHelper.PHONE,
                    signUpPhone.getText().toString());


            AppSharedPreferenceHelper.set(
                    AppSharedPreferenceHelper.KEY,
                    signUpResponse.getToken());
            progressDialog.dismiss();
            Toast.makeText(SignUpActivity.this, "Successful !!",
                    Toast.LENGTH_LONG).show();

            //OTP Verification
            Intent otpVerification = new Intent(SignUpActivity.this,
                    VerifyOTPActivity.class);
            startActivity(otpVerification);


        } else if (signUpResponse.message.equals("Unsuccessful")) {
            progressDialog.dismiss();
            Toast.makeText(SignUpActivity.this, "already existing",
                    Toast.LENGTH_LONG).show();

            AppSharedPreferenceHelper.set(
                    AppSharedPreferenceHelper.PHONE,
                    signUpPhone.getText().toString());


        } else {
            progressDialog.dismiss();
        }

    }


    @Override
    public void onFailureResult() {
        Toast.makeText(SignUpActivity.this, "Failed",
                Toast.LENGTH_LONG).show();
    }
}
