package c.su.vishwam;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TypeofLoginActivity extends AppCompatActivity {

    ImageView doctorType, receptionistType, nurseType, pharmacistType, emergencyType, adminType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeof_login);

        doctorType = (ImageView) findViewById(R.id.doctor_type);
        receptionistType = (ImageView) findViewById(R.id.receptionist_type);
        nurseType = (ImageView) findViewById(R.id.nurse_type);
        pharmacistType = (ImageView) findViewById(R.id.pharmacist_type);
        emergencyType = (ImageView) findViewById(R.id.emergency_type);
        adminType = (ImageView) findViewById(R.id.admin_type);
        doctorType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeofLoginActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        receptionistType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeofLoginActivity.this, ReceptionLoginActivity.class);
                startActivity(intent);
            }
        });
        nurseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeofLoginActivity.this, NurseLoginActivity.class);
                startActivity(intent);
            }
        });
        pharmacistType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeofLoginActivity.this, PharmacyLoginActivity.class);
                startActivity(intent);
            }
        });
        emergencyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeofLoginActivity.this, EmergencyLoginActivity.class);
                startActivity(intent);
            }
        });
        adminType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeofLoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
