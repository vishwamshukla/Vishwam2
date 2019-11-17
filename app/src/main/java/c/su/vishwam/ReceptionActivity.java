package c.su.vishwam;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ReceptionActivity extends AppCompatActivity {

    ImageView AddOpd, Profile, IpdRequest;
    TextView ipdRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        AddOpd = (ImageView) findViewById(R.id.reception_add_opd);

        Profile = (ImageView) findViewById(R.id.reception_profile);
        IpdRequest = findViewById(R.id.receptionist_ipd_request_image);

        ipdRequest = findViewById(R.id.receptionist_type_text);
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

        IpdRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceptionActivity.this, ReceptionIpdRequestActivity.class));
            }
        });
//        ipdRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ReceptionActivity.this, ReceptionIpdRequestActivity.class));
//            }
//        });
    }
}
