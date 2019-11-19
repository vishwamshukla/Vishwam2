package c.su.vishwam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NurseHomeActivity extends AppCompatActivity {

    ImageView ViewPatient, Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        ViewPatient = (ImageView) findViewById(R.id.reception_add_opd);

        Profile = (ImageView) findViewById(R.id.reception_profile);

        ViewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent (NurseHomeActivity.this , NurseViewPatientActivity.class));
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NurseHomeActivity.this, NurseProfileActivity.class));
            }
        });
    }
}