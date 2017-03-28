package in.co.snapqa.clientapp0903;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

public class TutorProfile extends AppCompatActivity {

    FloatingActionButton tutorProfileContactEdit, tutorProfileContactSave;
    RelativeLayout tutorProfileContactLayoutShow, tutorProfileContactLayoutEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tutorProfileContactEdit = (FloatingActionButton) findViewById(R.id.tutor_profile_contact_layout_edit_fab);
        tutorProfileContactSave = (FloatingActionButton) findViewById(R.id.tutor_profile_contact_layout_save);

        tutorProfileContactLayoutShow = (RelativeLayout) findViewById(R.id.tutor_profile_contact_layout);
        tutorProfileContactLayoutEdit = (RelativeLayout) findViewById(R.id.tutor_profile_contact_layout_edit);

        tutorProfileContactLayoutEdit.setVisibility(RelativeLayout.GONE);

        tutorProfileContactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorProfileContactLayoutEdit.setVisibility(RelativeLayout.VISIBLE);
                tutorProfileContactLayoutShow.setVisibility(RelativeLayout.GONE);
            }
        });

        tutorProfileContactSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorProfileContactLayoutEdit.setVisibility(RelativeLayout.GONE);
                tutorProfileContactLayoutShow.setVisibility(RelativeLayout.VISIBLE);

            }
        });

    }
}
