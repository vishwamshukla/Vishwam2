package c.su.vishwam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DoctorPrescriptionActivity extends AppCompatActivity {

    private EditText prescription;
    private Button submit;
    private String PatientPrescription;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_prescription);

        prescription = findViewById(R.id.doctor_prescription);
        submit = findViewById(R.id.doctor_submit_pres);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientPrescription = String.valueOf(prescription.getText());

                Id = getIntent().getStringExtra("id");


                DatabaseReference PatientRef = FirebaseDatabase.getInstance().getReference().child("Pharmacy").child("incoming");

                final HashMap<String, Object> ipdList = new HashMap<>();
                ipdList.put("prescription",PatientPrescription);


                PatientRef
                        .child(Id)
                        .updateChildren(ipdList)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(DoctorPrescriptionActivity.this, HomeActivity.class);
                                    Toast.makeText(DoctorPrescriptionActivity.this, "Prescription given to pharmacy", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    deleteData();
                                }
                            }
                        });

            }
        });
    }

    private void deleteData() {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");
        productsRef.child(Id).removeValue();
        startActivity(new Intent(DoctorPrescriptionActivity.this, HomeActivity.class));
    }
}
