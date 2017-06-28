package in.co.snapqa.clientapp0903.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keiferstone.nonet.NoNet;
import com.subhrajyoti.passwordview.PasswordView;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.models.SignUpRequest;
import in.co.snapqa.clientapp0903.models.SignUpResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phone";
    public static final String Key = "key";
    private static final String TAG = "";
    EditText signUpPhone, signUpPassword, signUpEmail, signUpName, signUpWhatsapp;
    Button signUpButton;
    SharedPreferences.Editor editor;
    PasswordView signupPassword;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String namePattern = "[a-zA-Z ]+[ ]+[a-zA-Z]+[a-zA-Z ]";
    SharedPreferences sharedpreferences;

    @Override
    protected void onStart() {
        super.onStart();
        NoNet.monitor(this)
                .poll()
                .snackbar()
                .start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensanslight.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        getSupportActionBar().setTitle("Sign Up");

        NoNet.configure()
                .endpoint("https://google.com")
                .timeout(5)
                .connectedPollFrequency(60)
                .disconnectedPollFrequency(1);

        signUpButton = (Button) findViewById(R.id.sugnupbutton);
        signUpEmail = (EditText) findViewById(R.id.signupemail);
        signUpName = (EditText) findViewById(R.id.signupname);
        //signUpPassword = (EditText) findViewById(R.id.signuppassword);
        signUpPhone = (EditText) findViewById(R.id.signupphone);
        signupPassword = (PasswordView) findViewById(R.id.signuppassword);
        signUpWhatsapp = (EditText) findViewById(R.id.signupwhatsapp);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        editor.putString(Phone, signUpPhone.getText().toString());
        editor.commit();



        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final API service = retrofit.create(API.class);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final SignUpRequest signUpRequest = new SignUpRequest("", "", "", "", "");

                signUpRequest.setPhone(signUpPhone.getText().toString());
                signUpRequest.setPassword(signupPassword.getText().toString());
                signUpRequest.setEmail(signUpEmail.getText().toString());
                signUpRequest.setName(signUpName.getText().toString());
                signUpRequest.setWhatsapp(signUpWhatsapp.getText().toString());

                Call<SignUpResponse> signUpResponseCall = service.signUp(signUpRequest);

                int phoneLength, passwordLength, emailLength, nameLength;
                phoneLength = signUpPhone.getText().length();
                passwordLength = signupPassword.getText().length();
                emailLength = signUpEmail.getText().length();
                nameLength = signUpName.getText().length();



                if(phoneLength == 10){
                    if(passwordLength > 8){
                        if(signUpEmail.getText().toString().matches(emailPattern) && emailLength > 0){
                            if(signUpName.getText().toString().matches(namePattern) && nameLength > 0){

                                progressDialog = ProgressDialog.show(SignUpActivity.this, "Just a sec!", "Signing you up!", true);
                                signUpResponseCall.enqueue(new Callback<SignUpResponse>() {



                                    @Override
                                    public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                                        SignUpResponse signUpResponse = response.body();
                                        int statusCode = response.code();
                                        Log.d("response:  ", ""+response.body().getToken());
                                        Log.d("Signup response  : ", ""+signUpResponse.toString());
                                        if(signUpResponse.message.equals("Successful")){
                                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_LONG).show();
                                            Log.d("status code: ", "" + statusCode);
                                            editor.putString(Phone, signUpPhone.getText().toString());
                                            editor.commit();

                                            editor.putString(Key, signUpResponse.getToken());
                                            editor.commit();

                                            progressDialog.dismiss();
                                            Toast.makeText(SignUpActivity.this, "Successful !!", Toast.LENGTH_LONG).show();

                                            //OTP Verification
                                            Intent otpVerification = new Intent(SignUpActivity.this, VerifyOTPActivity.class);
                                            startActivity(otpVerification);


                                        } else if(signUpResponse.message.equals("Unsuccessful")){
                                            progressDialog.dismiss();
                                            Toast.makeText(SignUpActivity.this, "already existing", Toast.LENGTH_LONG).show();
                                            Log.d("status code: ", "" + statusCode);

                                            editor.putString(Phone, signUpPhone.getText().toString());
                                            editor.commit();


                                        }else{
                                            progressDialog.dismiss();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<SignUpResponse> call, Throwable t) {

                                        Toast.makeText(SignUpActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else {

                                Toast.makeText(SignUpActivity.this, "Enter Full Name", Toast.LENGTH_LONG).show();
                            }
                        }else {

                            Toast.makeText(SignUpActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {

                        Toast.makeText(SignUpActivity.this, "Make Strong Password", Toast.LENGTH_LONG).show();
                    }
                }else {

                    Toast.makeText(SignUpActivity.this, "Incorrect Phone Number", Toast.LENGTH_LONG).show();
                }


            }
        });

    }


}
