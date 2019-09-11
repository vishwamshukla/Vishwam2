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

import java.util.concurrent.CompletionService;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;

public class OPDDetailsActivity extends AppCompatActivity {

    private TextView Name, Email, Phone, Age, Gender, RelationStatus, BloodGroup, Allergy, Weight, BP, Pulse, Complaints, Visit, ReferredBy, MedicalHistory;
    private String Id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opddetails);

        Name = findViewById(R.id.name_detail);
        Email = findViewById(R.id.email_detail);
        Phone = findViewById(R.id.phone_detail);
        Age = findViewById(R.id.age_detail);
        Gender = findViewById(R.id.gender_detail);
        RelationStatus = findViewById(R.id.relation_detail);
        BloodGroup = findViewById(R.id.bloodgroup_detail);
        Allergy = findViewById(R.id.allergy_detail);
        Weight = findViewById(R.id.weight_detail);
        BP = findViewById(R.id.bp_detail);
        Pulse = findViewById(R.id.pulse_detail);
        Complaints = findViewById(R.id.complaints_detail);
        Visit = findViewById(R.id.visit_detail);
        ReferredBy = findViewById(R.id.referredBy_detail);
        MedicalHistory = findViewById(R.id.medicalhistory_detail);

        Id = getIntent().getStringExtra("id");
        getPatientDetails(Id);
    }
    private void getPatientDetails(String id) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");


        productsRef.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Patients patients = dataSnapshot.getValue(Patients.class);

                    Name.setText(patients.getName());
                    Phone.setText(patients.getPhone());
                    RelationStatus.setText(patients.getRelation());
                    BloodGroup.setText(patients.getBloodgroup());
                    Weight.setText(patients.getWeight());
                    Allergy.setText(patients.getAllergy());
                    BP.setText(patients.getBp());
                    Pulse.setText(patients.getPulse());
                    Complaints.setText(patients.getComplaints());
                    MedicalHistory.setText(patients.getMedicalhistory());
                    Visit.setText(patients.getVisit());
                    ReferredBy.setText(patients.getReferredby());
                    Gender.setText(patients.getGender());
                    Age.setText(patients.getAge());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
