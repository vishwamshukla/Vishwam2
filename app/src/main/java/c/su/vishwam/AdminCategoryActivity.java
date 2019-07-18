package c.su.vishwam;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView opd, ipd;
    private ImageView emergency, leave,ipdTransfer,dischargeRequest,handoverRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        dischargeRequest = (ImageView) findViewById(R.id.discharge_request);
        opd = (ImageView) findViewById(R.id.opd);
        ipd = (ImageView) findViewById(R.id.ipd_btn);
        emergency = (ImageView) findViewById(R.id.emergency);
        leave = (ImageView) findViewById(R.id.leave);
        ipdTransfer = (ImageView) findViewById(R.id.ipd_request);
        handoverRequest = (ImageView) findViewById(R.id.handover_request);
        opd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminOpdActivity.class);
                intent.putExtra("category","opd");
                startActivity(intent);
            }
        });
        ipd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminIpdActivity.class);
                intent.putExtra("category","ipd");
                startActivity(intent);
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminEmergencyActivity.class);
                intent.putExtra("category","emergency");
                startActivity(intent);
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminLeaveActivity.class);
                intent.putExtra("category","ipd");
                startActivity(intent);
            }
        });
        ipdTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminIpdTransferActivity.class));
            }
        });
        dischargeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminCategoryActivity.this,AdminDischargeRequestActivity.class));
            }
        });
//        handoverRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AdminCategoryActivity.this,AdminHandoverRequestActivity.class));
//            }
//        });
    }
}
