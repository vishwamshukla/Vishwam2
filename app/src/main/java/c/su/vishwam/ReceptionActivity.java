package c.su.vishwam;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ReceptionActivity extends AppCompatActivity {

    ImageView AddOpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);

        AddOpd = (ImageView) findViewById(R.id.reception_add_opd);

        AddOpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReceptionActivity.this, Reception_add_opdActivity.class));
            }
        });
    }
}
