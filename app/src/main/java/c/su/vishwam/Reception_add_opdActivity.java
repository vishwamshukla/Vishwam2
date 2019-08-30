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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Reception_add_opdActivity extends AppCompatActivity {


    private String PatientName, PatientComplaints, PatientAllergy, saveCurrentDate, saveCurrentTime,PatientDob,PatientGender,PatientPhone, PatientEmail, PatientRelation, PatientBloodGroup, PatientWt, PatientBp, PatientPulse, PatientMedicalHistory, PatientVisit, PatientReferredBy;

    private Button Continue;
    private EditText name, email, phoneNumber, dob, gender, relationStatus, bloodGroup, allergy, wt, bp, pulse, complaints, medicalHistory, visit, referredBy;
    private String patientRandomKey;
    private DatabaseReference PatientRef;
    private ProgressDialog loadingBar;
    private long countPosts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception_add_opd);

        PatientRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");

        Continue = (Button) findViewById(R.id.p_button_continue);
        name = (EditText) findViewById(R.id.p_name1);
        email = (EditText) findViewById(R.id.p_email1);
        phoneNumber = (EditText) findViewById(R.id.p_phoneNumber1);
        dob = (EditText) findViewById(R.id.p_birthDate1);
        gender = (EditText) findViewById(R.id.p_gender1);
        relationStatus = (EditText) findViewById(R.id.p_relation_status1);
        bloodGroup = (EditText) findViewById(R.id.p_bloodGroup1);
        allergy = (EditText) findViewById(R.id.p_allergy1);
        wt = (EditText) findViewById(R.id.p_bmi1);
        bp = (EditText) findViewById(R.id.p_bp1);
        pulse = (EditText) findViewById(R.id.p_pulse1);
        complaints = (EditText) findViewById(R.id.p_complaints1);
        medicalHistory = (EditText) findViewById(R.id.p_medicalHistory1);
        visit = (EditText) findViewById(R.id.p_visit1);
        referredBy = (EditText) findViewById(R.id.p_referredBy1);
//        otherDetails = (EditText) findViewById(R.id.other_details);
//        age = (EditText) findViewById(R.id.age);
//        sex = (EditText) findViewById(R.id.sex);
//        phone = (EditText) findViewById(R.id.phone);

        loadingBar = new ProgressDialog(this);

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePatientData();
            }
        });

    }

    private void ValidatePatientData() {
        PatientName = String.valueOf(name.getText());
        PatientEmail = String.valueOf(email.getText());
        PatientPhone = String.valueOf(phoneNumber.getText());
        PatientDob = String.valueOf(dob.getText());
        PatientGender = String.valueOf(gender.getText());
        PatientRelation = String.valueOf(relationStatus.getText());
        PatientBloodGroup = String.valueOf(bloodGroup.getText());
        PatientAllergy = String.valueOf(allergy.getText());
        PatientWt = String.valueOf(wt.getText());
        PatientBp = String.valueOf(bp.getText());
        PatientPulse = String.valueOf(pulse.getText());
        PatientComplaints = String.valueOf(complaints.getText());
        PatientMedicalHistory = String.valueOf(medicalHistory.getText());
        PatientVisit = String.valueOf(visit.getText());
        PatientReferredBy = String.valueOf(referredBy.getText());


        if (TextUtils.isEmpty(PatientName)){
            Toast.makeText(this, "Please Enter the name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PatientComplaints)){
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

        PatientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    countPosts = dataSnapshot.getChildrenCount();
                }
                else {
                    countPosts = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        HashMap<String, Object> patientMap = new HashMap<>();
        patientMap.put("id", patientRandomKey);
        patientMap.put("name", PatientName);
        patientMap.put("email", PatientEmail);
        patientMap.put("phone", PatientPhone);
        patientMap.put("dob",PatientDob);
        patientMap.put("gender",PatientGender);
        patientMap.put("date",saveCurrentDate);
        patientMap.put("time",saveCurrentTime);
        patientMap.put("relation",PatientRelation);
        patientMap.put("bloodgroup",PatientBloodGroup);
        patientMap.put("allergy",PatientAllergy);
        patientMap.put("weight",PatientWt);
        patientMap.put("bp",PatientBp);
        patientMap.put("pulse",PatientPulse);
        patientMap.put("complaints",PatientComplaints);
        patientMap.put("medicalhistory",PatientMedicalHistory);
        patientMap.put("visit",PatientVisit);
        patientMap.put("referredby",PatientReferredBy);

        PatientRef.child(patientRandomKey).updateChildren(patientMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

//                            Intent intent = new Intent(Reception_add_opdActivity.this, AdminCategoryActivity.class);
//                            startActivity(intent);
                            finish();

                            loadingBar.dismiss();
                            Toast.makeText(Reception_add_opdActivity.this, "Patient added in OPD", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(Reception_add_opdActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
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
