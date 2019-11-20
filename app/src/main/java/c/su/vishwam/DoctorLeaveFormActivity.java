package c.su.vishwam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
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
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorLeaveFormActivity extends AppCompatActivity {

    private String DoctorName, DoctorFrom, DoctorTo, DocotorReason;

    private Button Continue;
    private EditText name, from, to ,reason;
    private String patientRandomKey;
    private DatabaseReference PatientRef;
    private ProgressDialog loadingBar;
    private long countPosts = 0;

    private CircleImageView profileImageView;
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_leave_form);

//        final Spinner genderSpinner = findViewById(R.id.p_gender1);
//        String genderText = genderSpinner.getSelectedItem().toString();
//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Reception_add_opdActivity.this,
//                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
//        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        genderSpinner.setAdapter(myAdapter);
//        genderSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                genderSpinner.setSelection(position);
//                String genderText = genderSpinner.getSelectedItem().toString();
//            }
//        });

        PatientRef = FirebaseDatabase.getInstance().getReference().child("Leave notes");
        progressBar = findViewById(R.id.progress_bar);

        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("patient image");

        profileImageView = (CircleImageView) findViewById(R.id.p_image1);

        Continue = (Button) findViewById(R.id.p_button_continue);
        name = (EditText) findViewById(R.id.leave_name1);
        from = findViewById(R.id.leave_from1);
        to = findViewById(R.id.leave_to1);
        reason = findViewById(R.id.leave_reason1);

//        otherDetails = (EditText) findViewById(R.id.other_details);
//        age = (EditText) findViewById(R.id.age);
//        sex = (EditText) findViewById(R.id.sex);
//        phone = (EditText) findViewById(R.id.phone);

        loadingBar = new ProgressDialog(this);

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (checker.equals("clicked"))
                {
                    pattientInfoSavedImage();
                }
                else
                {
                    ValidatePatientData();
                }
            }
        });

//        profileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                checker = "clicked";
//
//                CropImage.activity(imageUri)
//                        .setAspectRatio(1, 1)
//                        .start(DoctorLeaveFormActivity.this);
//            }
//        });

    }

    private void pattientInfoSavedImage() {
        if (TextUtils.isEmpty(name.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            //uploadImage();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

//            startActivity(new Intent(Reception_add_opdActivity.this, SettingsActivity.class));
//            finish();
        }
    }


    private void ValidatePatientData() {
        DoctorName = String.valueOf(name.getText());
        DoctorFrom = String.valueOf(from.getText());
        DoctorTo = String.valueOf(to.getText());
        DocotorReason = String.valueOf(reason.getText());


        if (TextUtils.isEmpty(DoctorName)){
            Toast.makeText(this, "Please Enter the name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DocotorReason)){
            Toast.makeText(this, "Please write the problem", Toast.LENGTH_SHORT).show();
        }
        else {
            StorePatientInformation();
        }

    }

    private void StorePatientInformation() {

//        loadingBar.setTitle("Adding....");
//        loadingBar.setMessage("Please wait......");
//        loadingBar.setCanceledOnTouchOutside(false);
//        loadingBar.show();
        progressBar.setVisibility(View.VISIBLE);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM");
        String saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        String saveCurrentTime = currentTime.format(calendar.getTime());

        patientRandomKey = saveCurrentTime + "-"+saveCurrentDate;

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
        patientMap.put("name", DoctorName);
        patientMap.put("from", DoctorFrom);
        patientMap.put("to", DoctorTo);
        patientMap.put("reason", DocotorReason);


        PatientRef.child(patientRandomKey).updateChildren(patientMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(DoctorLeaveFormActivity.this, HomeActivity.class);
                            startActivity(intent);
                            //finish();

                            //loadingBar.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(Reception_add_opdActivity.this, "Patient added in OPD", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //loadingBar.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(DoctorLeaveFormActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

//    private void uploadImage()
//    {
//        ValidatePatientData();
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//        patientRandomKey = saveCurrentTime + "-"+saveCurrentDate;
////        final ProgressDialog progressDialog = new ProgressDialog(this);
////        progressDialog.setTitle("Update Profile");
////        progressDialog.setMessage("Please wait, while we are updating your account information");
////        progressDialog.setCanceledOnTouchOutside(false);
////        progressDialog.show();
//        progressBar.setVisibility(View.VISIBLE);
//
//        if (imageUri != null)
//        {
//            final StorageReference fileRef = storageProfilePrictureRef
//                    .child(PatientPhone = String.valueOf(phoneNumber.getText())); //+ ".jpg");
//
//            uploadTask = fileRef.putFile(imageUri);
//
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull Task task) throws Exception
//                {
//                    if (!task.isSuccessful())
//                    {
//                        throw task.getException();
//                    }
//
//                    return fileRef.getDownloadUrl();
//                }
//            })
//                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task)
//                        {
//                            if (task.isSuccessful())
//                            {
//                                Uri downloadUrl = task.getResult();
//                                myUrl = downloadUrl.toString();
//
//                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");
//
//                                HashMap<String, Object> patientMap1 = new HashMap<>();
//                                patientMap1.put("id", patientRandomKey);
//                                patientMap1.put("name", PatientName);
//                                patientMap1.put("email", PatientEmail);
//                                patientMap1.put("phone", PatientPhone);
//                                patientMap1.put("age",PatientDob);
//                                patientMap1.put("gender",PatientGender);
//                                patientMap1.put("date",saveCurrentDate);
//                                patientMap1.put("time",saveCurrentTime);
//                                patientMap1.put("relation",PatientRelation);
//                                patientMap1.put("bloodgroup",PatientBloodGroup);
//                                patientMap1.put("allergy",PatientAllergy);
//                                patientMap1.put("weight",PatientWt);
//                                patientMap1.put("bp",PatientBp);
//                                patientMap1.put("pulse",PatientPulse);
//                                patientMap1.put("complaints",PatientComplaints);
//                                patientMap1.put("medicalhistory",PatientMedicalHistory);
//                                patientMap1.put("visit",PatientVisit);
//                                patientMap1.put("referredby",PatientReferredBy);
//                                patientMap1. put("image", myUrl);
//                                ref.child(patientRandomKey).updateChildren(patientMap1);
//
//                                //progressDialog.dismiss();
//                                progressBar.setVisibility(View.INVISIBLE);
//
//                                startActivity(new Intent(Reception_add_opdActivity.this, ReceptionActivity.class));
//                                //Toast.makeText(Reception_add_opdActivity.this, "Profile added successfully.", Toast.LENGTH_SHORT).show();
//                                //finish();
//                            }
//                            else
//                            {
//                                progressBar.setVisibility(View.INVISIBLE);
//                                //progressDialog.dismiss();
//                                Toast.makeText(Reception_add_opdActivity.this, "Error.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }
//        else
//        {
//            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
