package in.co.snapqa.clientapp0903;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SelectSubjectActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ToggleButton one, two, three;
    ArrayList<String> subjectList;
    LinearLayout mechSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        one = (ToggleButton) findViewById(R.id.sub1toggle);
        two = (ToggleButton) findViewById(R.id.thermodynamics);
        three = (ToggleButton) findViewById(R.id.fluid);


        mechSubjects = (LinearLayout) findViewById(R.id.mech_subjects);
        mechSubjects.setVisibility(LinearLayout.GONE);

        //one.setOnCheckedChangeListener(this);
        two.setOnCheckedChangeListener(this);
        three.setOnCheckedChangeListener(this);

        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mechSubjects.setVisibility(LinearLayout.VISIBLE);
                }else{
                    mechSubjects.setVisibility(LinearLayout.GONE);
                }
            }
        });

        subjectList = new ArrayList<String>();

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            Drawable drawable = getResources().getDrawable(R.drawable.button_rounded_corners_blue);
            buttonView.setBackground(drawable);
            buttonView.setTextColor(Color.parseColor("#FFFFFF"));
            subjectList.add(buttonView.getText().toString());
        }else {
            Drawable drawable = getResources().getDrawable(R.drawable.button_rounded_corners);
            buttonView.setBackground(drawable);
            buttonView.setTextColor(Color.parseColor("#03A9F4"));
            for(int i=0; i<subjectList.size(); i++){
                if(buttonView.getText().toString().equals(subjectList.get(i))){

                    subjectList.remove(i);

                }
            }
        }


    }
}
