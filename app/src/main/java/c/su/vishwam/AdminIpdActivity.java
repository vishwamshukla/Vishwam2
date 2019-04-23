package c.su.vishwam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import c.su.vishwam.Model.Users;
import c.su.vishwam.Prevalent.Prevalent;

import static c.su.vishwam.R.id.ipd_add_patient;

public class AdminIpdActivity extends AppCompatActivity {

    private String PatientName, PatientProblem, OtherDetails, saveCurrentDate, saveCurrentTime,PatientAge,PatientSex,PatientWard,PatientBed;

    private Button AddNewIpdPatient;
    private EditText name, problem, otherDetails,age,sex,ward,bed;
    private String patientRandomKey;
    private DatabaseReference PatientRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ipd);

        PatientRef = FirebaseDatabase.getInstance().getReference().child("Patient IPD").child("7057655824");


        AddNewIpdPatient = findViewById(ipd_add_patient);

        AddNewIpdPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePatientData();
            }
        });
        name = findViewById(R.id.ipd_name);

        problem = findViewById(R.id.ipd_problem);

        otherDetails = findViewById(R.id.ipd_other_details);
        age = findViewById(R.id.ipd_age);
        sex = findViewById(R.id.ipd_sex);
        ward = findViewById(R.id.ipd_ward_no);
        bed = findViewById(R.id.ipd_bed_no);

        loadingBar = new ProgressDialog(this);


    }

    private void ValidatePatientData() {
        PatientName = String.valueOf(name.getText());
        PatientProblem = String.valueOf(problem.getText());
        OtherDetails = String.valueOf(otherDetails.getText());
        PatientAge = String.valueOf(age.getText());
        PatientSex = String.valueOf(sex.getText());
        PatientWard = String.valueOf(ward.getText());
        PatientBed = String.valueOf(bed.getText());

        if (TextUtils.isEmpty(PatientName)){
            Toast.makeText(this, "Please Enter the name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PatientProblem)){
            Toast.makeText(this, "PLease write the problem", Toast.LENGTH_SHORT).show();
        }
        else {
            StorePatientInformation();
        }

    }

    private void StorePatientInformation() {

        loadingBar.setTitle("Adding....");
        loadingBar.setMessage("Please wait......");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        patientRandomKey = saveCurrentDate + "-"+saveCurrentTime;


        HashMap<String, Object> patientMap = new HashMap<>();
        patientMap.put("id", patientRandomKey);
        patientMap.put("name", PatientName);
        patientMap.put("problem", PatientProblem);
        patientMap.put("others", OtherDetails);
        patientMap.put("sex",PatientSex);
        patientMap.put("age",PatientAge);
        patientMap.put("ward",PatientWard);
        patientMap.put("bed",PatientBed);
        patientMap.put("date",saveCurrentDate);
        patientMap.put("time",saveCurrentTime);

        PatientRef.child(patientRandomKey).updateChildren(patientMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(AdminIpdActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);
                            finish();

                            loadingBar.dismiss();
                            Toast.makeText(AdminIpdActivity.this, "Patient added in IPD", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(AdminIpdActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
