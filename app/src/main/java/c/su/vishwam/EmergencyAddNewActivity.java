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

public class EmergencyAddNewActivity extends AppCompatActivity {

    private String PatientName, PatientDetails, saveCurrentDate, saveCurrentTime, PatientAge, PatientGender;
    private Button Continue;
    private EditText name, details,age, gender;
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
        setContentView(R.layout.activity_emergency_add_new);

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

        PatientRef = FirebaseDatabase.getInstance().getReference().child("Emergency");
        progressBar = findViewById(R.id.progress_bar);

        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("emergency image");

        profileImageView = (CircleImageView) findViewById(R.id.p_image1);

        Continue = (Button) findViewById(R.id.p_button_continue);
        name = (EditText) findViewById(R.id.emergency_name1);
        details = findViewById(R.id.emergency_details1);

        age = findViewById(R.id.emergency_age1);
        gender = findViewById(R.id.emergency_gender1);
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

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checker = "clicked";

                CropImage.activity(imageUri)
                        .setAspectRatio(1, 1)
                        .start(EmergencyAddNewActivity.this);
            }
        });

    }

    private void pattientInfoSavedImage() {
        if (TextUtils.isEmpty(name.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked"))
        {
            uploadImage();
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
        PatientName = String.valueOf(name.getText());
        PatientDetails = String.valueOf(details.getText());
        PatientAge = String.valueOf(age.getText());
        PatientGender= String.valueOf(gender.getText());

        if (TextUtils.isEmpty(PatientName)){
            Toast.makeText(this, "Please Enter the name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(PatientDetails)){
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
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calendar.getTime());

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
        patientMap.put("name", PatientName);
        patientMap.put("details", PatientDetails);
        patientMap.put("age", PatientAge);
        patientMap.put("gender", PatientGender);

        PatientRef.child(patientRandomKey).updateChildren(patientMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(EmergencyAddNewActivity.this, EmergencyHomeActivity.class);
                            startActivity(intent);
                            //finish();

                            //loadingBar.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);
                            //Toast.makeText(Reception_add_opdActivity.this, "Patient added in OPD", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //loadingBar.dismiss();
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(EmergencyAddNewActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void uploadImage()
    {
        ValidatePatientData();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MMM");
        saveCurrentDate = currentDate.format(calendar.getTime());


        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calendar.getTime());
        patientRandomKey = saveCurrentTime + "-"+saveCurrentDate;
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Update Profile");
//        progressDialog.setMessage("Please wait, while we are updating your account information");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(PatientName = String.valueOf(name.getText())); //+ ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Emergency");

                                HashMap<String, Object> patientMap1 = new HashMap<>();
                                patientMap1.put("id", patientRandomKey);
                                patientMap1.put("name", PatientName);
                                patientMap1.put("details", PatientDetails);
                                patientMap1.put("age", PatientAge);
                                patientMap1.put("gender", PatientGender);
                                patientMap1.put("image", myUrl);
                                ref.child(patientRandomKey).updateChildren(patientMap1);

                                //progressDialog.dismiss();
                                progressBar.setVisibility(View.INVISIBLE);

                                startActivity(new Intent(EmergencyAddNewActivity.this, EmergencyHomeActivity.class));
                                //Toast.makeText(Reception_add_opdActivity.this, "Profile added successfully.", Toast.LENGTH_SHORT).show();
                                //finish();
                            }
                            else
                            {
                                progressBar.setVisibility(View.INVISIBLE);
                                //progressDialog.dismiss();
                                Toast.makeText(EmergencyAddNewActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}