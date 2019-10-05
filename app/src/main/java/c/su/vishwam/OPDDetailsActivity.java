package c.su.vishwam;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.CompletionService;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;
import de.hdodenhof.circleimageview.CircleImageView;

public class OPDDetailsActivity extends AppCompatActivity {

    private TextView Name, Email, Phone, Age, Gender, RelationStatus, BloodGroup, Allergy, Weight, BP, Pulse, Complaints, Visit, ReferredBy, MedicalHistory;
    private String Id = "", saveCurrentDate, saveCurrentTime,patientRandomKey;
    private CircleImageView profileImageView;
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private Button complete;
    private DatabaseReference PatientRef;

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
        complete = (Button) findViewById(R.id.complete_button);

        Id = getIntent().getStringExtra("id");
        getPatientDetails(Id);

        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("patient image");
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");
        patientImageDisplay(profileImageView);
        progressBar = findViewById(R.id.progress_bar);
        //progressBar.setVisibility(View.VISIBLE);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteAppointment();
            }
        });
    }

    private void CompleteAppointment() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calendar.getTime());

        patientRandomKey = saveCurrentTime + "-"+saveCurrentDate;

        PatientRef = FirebaseDatabase.getInstance().getReference().child("Patients History");

        HashMap<String, Object> patientMap = new HashMap<>();
        patientMap.put("id", patientRandomKey);
        patientMap.put("name", Name);
        patientMap.put("email", Email);
        patientMap.put("phone", Phone);
        patientMap.put("age",Age);
        patientMap.put("gender",Gender);
        patientMap.put("date",saveCurrentDate);
        patientMap.put("time",saveCurrentTime);
        patientMap.put("bloodgroup",BloodGroup);
        patientMap.put("allergy",Allergy);
        patientMap.put("weight",Weight);
        patientMap.put("bp",BP);
        patientMap.put("relation",RelationStatus);
        patientMap.put("pulse",Pulse);
        patientMap.put("complaints",Complaints);
        patientMap.put("medicalhistory",MedicalHistory);
        patientMap.put("visit",Visit);
        patientMap.put("referredby",ReferredBy);

        PatientRef.child(Id).updateChildren(patientMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(OPDDetailsActivity.this, HomeActivity.class);
                            startActivity(intent);
                            //finish();

                            //loadingBar.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(Reception_add_opdActivity.this, "Patient added in OPD", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //loadingBar.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(OPDDetailsActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");
        productsRef.child(Id).removeValue();
        startActivity(new Intent(OPDDetailsActivity.this, HomeActivity.class));
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
