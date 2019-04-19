package c.su.vishwam;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;

public class IpdDetailsActivity extends AppCompatActivity {

    private TextView Name, Problem, OtherDetails, Sex, Age,Pulse,BloodPresuure,BloodGroup,anemia,MedicalHistory,Allergies,courseinHospital;
    private String Id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipd_details);

        Name = (TextView) findViewById(R.id.patient_name);
        Problem = (TextView) findViewById(R.id.patient_problem);
        OtherDetails = (TextView) findViewById(R.id.patient_other_details);
        Sex = (TextView) findViewById(R.id.patient_sex);
        Age = (TextView) findViewById(R.id.patient_age);
        Pulse = (TextView) findViewById(R.id.patient_pulse);
        BloodPresuure = (TextView) findViewById(R.id.patient_bp);
        BloodGroup = (TextView) findViewById(R.id.patient_bloodgroup);
        anemia = (TextView) findViewById(R.id.patient_anemia);
        MedicalHistory = (TextView) findViewById(R.id.patient_medicalHistory);
        Allergies = (TextView) findViewById(R.id.patient_allegies);
        courseinHospital = (TextView) findViewById(R.id.patient_courseinHospital);

        Id = getIntent().getStringExtra("id");

        getPatientDetails(Id);
    }
    private void getPatientDetails(String id) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patient IPD").child(Prevalent.currentOnlineUser.getPhone());

        productsRef.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Patients patients = dataSnapshot.getValue(Patients.class);

                    Name.setText("Name: "+patients.getName());
                    Problem.setText("Issue: "+patients.getProblem());
                    OtherDetails.setText("Details: "+patients.getOthers());
                    Sex.setText("Sex: "+patients.getSex());
                    Age.setText("Age: "+patients.getAge());
                    Pulse.setText("Pulse: "+patients.getPulse());
                    BloodPresuure.setText("Blood Pressure: "+patients.getBp());
                    BloodGroup.setText("Blood Group: "+patients.getBloodgroup());
                    anemia.setText("Anaemia: "+patients.getAnemia());
                    MedicalHistory.setText("Medical History: "+patients.getMedicalhistory());
                    Allergies.setText("Allergies: "+patients.getAllergies());
                    courseinHospital.setText("Course in hospital: "+patients.getCourseinhospital());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
