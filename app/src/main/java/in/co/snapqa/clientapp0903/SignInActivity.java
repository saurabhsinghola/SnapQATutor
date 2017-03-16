package in.co.snapqa.clientapp0903;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import in.co.snapqa.clientapp0903.interfaces.API;
import in.co.snapqa.clientapp0903.models.TokenRequest;
import in.co.snapqa.clientapp0903.models.TokenResponse;
import in.co.snapqa.clientapp0903.network.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {

    Button signInButton, signUpActivity;
    EditText emailId, password, fullName, phone;

    TextView signUpText;

    ProgressDialog progressDialog;

    private static final String TAG = "SignIn";

    int statusCode;

    SharedPreferences sharedpreferences;

    String email, pass, responseServer, phoneNumber, fName;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Key = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_sign_in);


        signInButton = (Button) findViewById(R.id.signinbutton);
        signUpText = (TextView) findViewById(R.id.signUpText);
        password = (EditText) findViewById(R.id.signinpassword);
        phone = (EditText) findViewById(R.id.signinphone);

        pass = password.getText().toString();
        phoneNumber = phone.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final API service = retrofit.create(API.class);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = ProgressDialog.show(SignInActivity.this, "Just a sec!", "Logging you in", true);

                final TokenRequest tokenRequest = new TokenRequest();

                tokenRequest.setPhone(phoneNumber);
                tokenRequest.setPassword(pass);

                Call<TokenResponse> tokenResponseCall = service.getTokenAccess(tokenRequest);

                tokenResponseCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                        TokenResponse tokenResponse = response.body();
                        statusCode = response.code();
                        //String token = tokenResponse.getToken();
                        Toast.makeText(SignInActivity.this, "Success", Toast.LENGTH_LONG).show();
                        Log.d("status code: ", "" + statusCode);

                        //For Saving Token

                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString(Key, tokenResponse.token);

                        editor.commit();

                        //Log.d(TAG, "token response:::" + token);
                        Log.d(TAG, "token response.token:::" + response.body());
                        Log.d(TAG, "token response.gettoken:::" +tokenResponse.getToken());


                        progressDialog.dismiss();

                        //Changing Activity
                        //Intent changeActivity = new Intent(SignInActivity.this, MainActivity.class);
                        //startActivity(changeActivity);

                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t) {
                        Toast.makeText(SignInActivity.this, "Failed", Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                    }
                });

                Intent mainActivity = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(mainActivity);

            }
        });


        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeActivity = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(changeActivity);
            }
        });


    }
}
