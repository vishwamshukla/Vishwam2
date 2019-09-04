package c.su.vishwam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
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
import java.util.SimpleTimeZone;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;

public class PatientDetailsActivity extends AppCompatActivity {

    private TextView Name, Problem, OtherDetails, Sex, Age,Time,Date;
    private String Id = "";
    private Button TransferToIpd, TransferToDoctor, CancelAppoinment, CompleteAppoinment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        Name = (TextView) findViewById(R.id.patient_name);
        Problem = (TextView) findViewById(R.id.patient_problem);
        OtherDetails = (TextView) findViewById(R.id.patient_other_details);
        Sex = (TextView) findViewById(R.id.patient_sex);
        Age = (TextView) findViewById(R.id.patient_age);

        Id = getIntent().getStringExtra("id");
       // TransferToIpd = (Button) findViewById(R.id.transfer_ipd);
      //  TransferToDoctor = (Button) findViewById(R.id.transfer_to_doctor);
        //CancelAppoinment = (Button) findViewById(R.id.cancel_appoinment);
//        CompleteAppoinment = (Button)  findViewById(R.id.complete);


        getPatientDetails(Id);

//        TransferToIpd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addingToIpdList();
//            }
//        });
//        TransferToDoctor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PatientDetailsActivity.this,DoctorListActivity.class));
//            }
//        });
//        CancelAppoinment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Id = getIntent().getStringExtra("id");
//                RemovePatient(Id);
//            }
//        });
//        CompleteAppoinment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

//    private void RemovePatient(String id) {
//        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");
//        productsRef.child("id").removeValue();
//    }

//    private void addingToIpdList() {
//        String saveCurrentTime, saveCurrentDate;
//
//        Calendar calForDate = Calendar.getInstance();
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
//        saveCurrentDate = currentDate.format(calForDate.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm a");
//        saveCurrentTime = currentDate.format(calForDate.getTime());
//
//        final DatabaseReference IpdListRef = FirebaseDatabase.getInstance().getReference().child("Patient IPD");
//
//        final HashMap<String, Object> ipdList = new HashMap<>();
//        ipdList.put("id",Id);
//        ipdList.put("name",Name.getText().toString());
//        ipdList.put("problem",Problem.getText().toString());
//        ipdList.put("others",OtherDetails.getText().toString());
//        ipdList.put("sex",Sex.getText().toString());
//        ipdList.put("age",Age.getText().toString());
//
//        IpdListRef.child(Prevalent.currentOnlineUser.getPhone())
//                .child(Id)
//                .updateChildren(ipdList)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            IpdListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
//                                    .child("Patients").child(Id)
//                                    .updateChildren(ipdList)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
//                                                Toast.makeText(PatientDetailsActivity.this, "Patient added!", Toast.LENGTH_SHORT).show();
//
//                                                Intent intent = new Intent(PatientDetailsActivity.this,HomeActivity.class);
//                                                startActivity(intent);
//
//                                            }                                        }
//                                    });
//                        }
//                    }
//                });
//    }

    private void getPatientDetails(String id) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)").child(Prevalent.currentOnlineUser.getPhone());


        productsRef.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Patients patients = dataSnapshot.getValue(Patients.class);

                    Name.setText(patients.getName());
                    Problem.setText(patients.getProblem());
                    OtherDetails.setText(patients.getOthers());
                    Sex.setText(patients.getSex());
                    Age.setText(patients.getAge());
                    Date.setText(patients.getDate());
                    Time.setText(patients.getTime());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
