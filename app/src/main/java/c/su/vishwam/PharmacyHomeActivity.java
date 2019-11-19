package c.su.vishwam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PharmacyHomeActivity extends AppCompatActivity {



    private ImageView profile, viewPres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_home);

        profile = findViewById(R.id.reception_profile);
        viewPres = findViewById(R.id.view_prescription_pharmacy);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PharmacyHomeActivity.this, PharmacyProfileActivity.class));
            }
        });
        viewPres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PharmacyHomeActivity.this, PharamcyViewPresActivity.class));
            }
        });
    }
}
