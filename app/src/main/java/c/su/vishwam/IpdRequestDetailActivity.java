package c.su.vishwam;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import c.su.vishwam.Model.Patients;
import de.hdodenhof.circleimageview.CircleImageView;

public class IpdRequestDetailActivity extends AppCompatActivity {

    private TextView Name, Email, Phone, Age, Gender, RelationStatus, BloodGroup, Allergy, Weight, BP, Pulse, Complaints, Visit, ReferredBy, MedicalHistory;
    private String Id = "", saveCurrentDate, saveCurrentTime,patientRandomKey;
    private CircleImageView profileImageView;
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private DatabaseReference PatientRef;

    private ProgressBar progressBar;
    private String checker = "";

    private Button approve, decline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipd_request_detail);

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

       approve = findViewById(R.id.addAndApprove_Button);
       decline = findViewById(R.id.decline_ipdRequest);



        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saveCurrentTime, saveCurrentDate;

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM");
                saveCurrentDate = currentDate.format(calendar.getTime());


                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                saveCurrentTime = currentTime.format(calendar.getTime());

                patientRandomKey = saveCurrentTime + "-"+saveCurrentDate;


                final DatabaseReference IpdListRef = FirebaseDatabase.getInstance().getReference().child("from ipd request");

                final HashMap<String, Object> ipdList = new HashMap<>();
                ipdList.put("id",patientRandomKey);
                ipdList.put("name",Name.getText().toString());
                ipdList.put("email", Email.getText().toString());
                ipdList.put("phone", Phone.getText().toString());
                ipdList.put("age",Age.getText().toString());
                ipdList.put("gender",Gender.getText().toString());
//                ipdList.put("date",saveCurrentDate);
//                ipdList.put("time",saveCurrentTime);
                ipdList.put("bloodgroup",BloodGroup.getText().toString());
                ipdList.put("allergy",Allergy.getText().toString());
                ipdList.put("weight",Weight.getText().toString());
                ipdList.put("bp",BP.getText().toString());
                ipdList.put("relation",RelationStatus.getText().toString());
                ipdList.put("pulse",Pulse.getText().toString());
                ipdList.put("complaints",Complaints.getText().toString());
                ipdList.put("medicalhistory",MedicalHistory.getText().toString());
                ipdList.put("visit",Visit.getText().toString());
                ipdList.put("referredby",ReferredBy.getText().toString());


                IpdListRef
                        .child(Id)
                        .updateChildren(ipdList)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(IpdRequestDetailActivity.this, HomeActivity.class);
                                    Toast.makeText(IpdRequestDetailActivity.this, "Appointment completed", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    deleteData();
                                }
                            }
                        });
            }
        });
    }

//    private void ipdRequest() {
//        String saveCurrentTime, saveCurrentDate;
//
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//
//        patientRandomKey = saveCurrentTime + "-"+saveCurrentDate;
//
//
//        final DatabaseReference IpdListRef = FirebaseDatabase.getInstance().getReference().child("ipd requests");
//
//        final HashMap<String, Object> ipdList = new HashMap<>();
//        ipdList.put("id",patientRandomKey);
//        ipdList.put("name",Name.getText().toString());
//        ipdList.put("email", Email.getText().toString());
//        ipdList.put("phone", Phone.getText().toString());
//        ipdList.put("age",Age.getText().toString());
//        ipdList.put("gender",Gender.getText().toString());
////                ipdList.put("date",saveCurrentDate);
////                ipdList.put("time",saveCurrentTime);
//        ipdList.put("bloodgroup",BloodGroup.getText().toString());
//        ipdList.put("allergy",Allergy.getText().toString());
//        ipdList.put("weight",Weight.getText().toString());
//        ipdList.put("bp",BP.getText().toString());
//        ipdList.put("relation",RelationStatus.getText().toString());
//        ipdList.put("pulse",Pulse.getText().toString());
//        ipdList.put("complaints",Complaints.getText().toString());
//        ipdList.put("medicalhistory",MedicalHistory.getText().toString());
//        ipdList.put("visit",Visit.getText().toString());
//        ipdList.put("referredby",ReferredBy.getText().toString());
//
//        IpdListRef
//                .child(Id)
//                .updateChildren(ipdList)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            patientImageDisplay1(profileImageView);
//                            Intent intent = new Intent(IpdRequestDetailActivity.this, HomeActivity.class);
//                            Toast.makeText(IpdRequestDetailActivity.this, "Details shared.", Toast.LENGTH_SHORT).show();
//                            startActivity(intent);
//                            deleteData();
//                        }
//                    }
//                });
//    }

    private void deleteData() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");
        productsRef.child(Id).removeValue();
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(IpdRequestDetailActivity.this, HomeActivity.class));
    }

//    private void CompleteAppointment() {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//
//        patientRandomKey = saveCurrentTime + "-"+saveCurrentDate;
//
//        PatientRef = FirebaseDatabase.getInstance().getReference().child("Patients History");
//
//        HashMap<String, Object> patientMap = new HashMap<>();
//        patientMap.put("id", patientRandomKey);
//        patientMap.put("name", Name);
//        patientMap.put("email", Email);
//        patientMap.put("phone", Phone);
//        patientMap.put("age",Age);
//        patientMap.put("gender",Gender);
//        patientMap.put("date",saveCurrentDate);
//        patientMap.put("time",saveCurrentTime);
//        patientMap.put("bloodgroup",BloodGroup);
//        patientMap.put("allergy",Allergy);
//        patientMap.put("weight",Weight);
//        patientMap.put("bp",BP);
//        patientMap.put("relation",RelationStatus);
//        patientMap.put("pulse",Pulse);
//        patientMap.put("complaints",Complaints);
//        patientMap.put("medicalhistory",MedicalHistory);
//        patientMap.put("visit",Visit);
//        patientMap.put("referredby",ReferredBy);
//
//        PatientRef.child(Id).updateChildren(patientMap)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//
//                            Intent intent = new Intent(IpdRequestDetailActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            //finish();
//
//                            //loadingBar.dismiss();
//                            progressBar.setVisibility(View.INVISIBLE);
//                            //Toast.makeText(Reception_add_opdActivity.this, "Patient added in OPD", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            //loadingBar.dismiss();
//                            progressBar.setVisibility(View.INVISIBLE);
//                            Toast.makeText(IpdRequestDetailActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }


    private void getPatientDetails(String id) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("ipd requests");


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
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("ipd requests").child(Id);

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
    private void patientImageDisplay1(final CircleImageView profileImageView) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("ipd requests").child(Id);

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

                        final DatabaseReference IpdListRef = FirebaseDatabase.getInstance().getReference().child("ipd requests");

                        final HashMap<String, Object> ipdList = new HashMap<>();
                        ipdList.put("image",image);

                        IpdListRef
                                .child(Id)
                                .updateChildren(ipdList)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){

                                            Toast.makeText(IpdRequestDetailActivity.this, "image shared.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
