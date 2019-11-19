package c.su.vishwam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class EmergencyHomeActivity extends AppCompatActivity {

    private ImageView profile, AddEmergency, ViewEmergency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_home);

        profile = findViewById(R.id.reception_profile);

        AddEmergency = findViewById(R.id.emergency_add_new);
        ViewEmergency = findViewById(R.id.emergency_view);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmergencyHomeActivity.this, EmergencyProfileActivity.class));
            }
        });

        AddEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmergencyHomeActivity.this, EmergencyAddNewActivity.class));
            }
        });

        ViewEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmergencyHomeActivity.this, EmergencyViewActivity.class));
            }
        });
    }
}
