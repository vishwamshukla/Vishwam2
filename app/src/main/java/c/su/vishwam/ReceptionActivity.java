package c.su.vishwam;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ReceptionActivity extends AppCompatActivity {

    ImageView AddOpd, Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        AddOpd = (ImageView) findViewById(R.id.reception_add_opd);

        Profile = (ImageView) findViewById(R.id.reception_profile);

        AddOpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceptionActivity.this, Reception_add_opdActivity.class));
            }
        });
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceptionActivity.this, ReceptionProfileActivity.class));
            }
        });
    }
}
