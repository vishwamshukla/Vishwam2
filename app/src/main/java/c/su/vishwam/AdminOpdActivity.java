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
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminOpdActivity extends AppCompatActivity {

    private String PatientName, PatientProblem, OtherDetails, saveCurrentDate, saveCurrentTime;

    private Button AddNewPatient;
    private EditText name, problem, otherDetails;
    private String patientRandomKey;
    private DatabaseReference PatientRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_opd);

        PatientRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");

        AddNewPatient = (Button) findViewById(R.id.add_patient);
        name = (EditText) findViewById(R.id.name);

        problem = (EditText) findViewById(R.id.problem);

        otherDetails = (EditText) findViewById(R.id.other_details);
        loadingBar = new ProgressDialog(this);

        AddNewPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePatientData();
            }
        });

    }

    private void ValidatePatientData() {
        PatientName = name.getText().toString();
        PatientProblem = problem.getText().toString();
        OtherDetails = otherDetails.getText().toString();

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

        PatientRef.child(patientRandomKey).updateChildren(patientMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(AdminOpdActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminOpdActivity.this, "Patient added in OPD", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(AdminOpdActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
