package in.co.snapqa.clientapp0903.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.snapqa.clientapp0903.R;
import in.co.snapqa.clientapp0903.base.BaseActivity;
import in.co.snapqa.clientapp0903.controller.TutorController;
import in.co.snapqa.clientapp0903.interfaces.ApiControllerListener;
import in.co.snapqa.clientapp0903.models.UserProfileResponse;
import in.co.snapqa.clientapp0903.utils.AppSharedPreferenceHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TutorProfile extends BaseActivity implements ApiControllerListener {

    TextView phoneNumber, whatsappNumber, email, accountNumber, IFSCCode, specialization,
            currentBalance, totalEarning, panNumber;
    //    SharedPreferences sharedpreferences;
    String rating, name, specializationString = "";
    ArrayList<String> specs;
    FloatingActionButton paymentEditFAB, specializationEditFAB;
//    SharedPreferences.Editor editor;

    CollapsingToolbarLayout collapsingToolbarLayout;

//    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
//        TODO KILL Current Activity
        moveNextTo(MainActivity.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected String getActivityTitle() {
        return "Profile";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String token =
                AppSharedPreferenceHelper.getString(AppSharedPreferenceHelper.KEY, "notPresent");


        phoneNumber = (TextView) findViewById(R.id.tutor_profile_phone_number);
        whatsappNumber = (TextView) findViewById(R.id.tutor_profile_whatsapp_number);
        email = (TextView) findViewById(R.id.tutor_profile_email_id);
        accountNumber = (TextView) findViewById(R.id.tutor_profile_account_number);
        IFSCCode = (TextView) findViewById(R.id.tutor_profile_ifsc_code);
        specialization = (TextView) findViewById(R.id.tutor_profile_specialization);
        currentBalance = (TextView) findViewById(R.id.tutor_profile_current_balance);
        totalEarning = (TextView) findViewById(R.id.tutor_profile_total_earning);
        panNumber = (TextView) findViewById(R.id.tutor_profile_pan_number);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        paymentEditFAB =
                (FloatingActionButton) findViewById(R.id.tutor_profile_payment_details_edit);
        specializationEditFAB =
                (FloatingActionButton) findViewById(R.id.tutor_profile_specialization_edit);

        specializationString = "";
        showMessageDialog("Just a sec!", "Loading Details");

        specializationEditFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPreferenceHelper.set(AppSharedPreferenceHelper.SUBJECTS, "Subjects");
                moveNextTo(SelectSubjectActivity.class);
            }
        });

        new TutorController(this, this).callUserProfile();

        paymentEditFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSharedPreferenceHelper.set(AppSharedPreferenceHelper.BANK, "Bank");
                moveNextTo(PaymentDetailsActivity.class);
            }
        });

    }

    @Override
    public void onSuccessResult(Object profileResponse) {
        UserProfileResponse response = (UserProfileResponse) profileResponse;
        rating = String.valueOf(response.getRating());
        name = response.getName() + "(" + rating + ")";
        collapsingToolbarLayout.setTitle(name);
        Typeface typeface = Typeface.createFromAsset(TutorProfile.this.getAssets(),
                "fonts/opensanslight.ttf");
        collapsingToolbarLayout.setExpandedTitleTypeface(typeface);
        collapsingToolbarLayout.setCollapsedTitleTypeface(typeface);
        phoneNumber.setText(response.getPhone());
        whatsappNumber.setText(response.getPhone());
        email.setText(response.getEmail());
        currentBalance.setText(String.valueOf(response.getPoints()));
        totalEarning.setText(String.valueOf(response.getPoints()));
        specs = (ArrayList<String>) response.getSpecialization();
        if (specs.isEmpty()) {
            Log.d("no", " subjects ");
        } else {
            for (int i = 0; i < specs.size(); i++) {

                if (i == 0) {
                    specializationString = specs.get(i).toString();
                } else {
                    specializationString =
                            specializationString + "\n" + specs.get(i).toString();
                    Log.d("spe str  ", "  " + specializationString);
                    specialization.setText(specializationString);
                }

            }
        }
        panNumber.setText(response.getBankDetails().getPanNumber());
        IFSCCode.setText(response.getBankDetails().getiFSC());
        accountNumber.setText(response.getBankDetails().getAccNo());

    }

    @Override
    public void onFailureResult() {

    }
}
