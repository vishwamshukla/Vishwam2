package c.su.vishwam;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.concurrent.CompletionService;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;
import de.hdodenhof.circleimageview.CircleImageView;

public class OPDDetailsActivity extends AppCompatActivity {

    private TextView Name, Email, Phone, Age, Gender, RelationStatus, BloodGroup, Allergy, Weight, BP, Pulse, Complaints, Visit, ReferredBy, MedicalHistory;
    private String Id = "";
    private CircleImageView profileImageView;
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;

    private ProgressBar progressBar;
    private String checker = "";
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
        profileImageView = (CircleImageView) findViewById(R.id.patient_detail_image);

        Id = getIntent().getStringExtra("id");
        getPatientDetails(Id);

        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("patient image");
        patientImageDisplay(profileImageView);
        progressBar = findViewById(R.id.progress_bar);
        //progressBar.setVisibility(View.VISIBLE);
    }



    private void getPatientDetails(String id) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");


        productsRef.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Patients patients = dataSnapshot.getValue(Patients.class);

                    Name.setText(patients.getName());
                    Email.setText(patients.getEmail());
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
    private void patientImageDisplay(final CircleImageView profileImageView) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)").child(Id);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("image").exists())
                    {
                        //progressBar.setVisibility(View.INVISIBLE);
                        String image = String.valueOf(dataSnapshot.child("image").getValue());

                        Picasso.get().load(image).into(profileImageView);

                    }

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
