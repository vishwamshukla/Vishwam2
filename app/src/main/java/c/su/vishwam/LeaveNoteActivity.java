package c.su.vishwam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class LeaveNoteActivity extends AppCompatActivity {

    private String DoctorName,DoctorFrom,DoctorTo,saveCurrentDate, saveCurrentTime,DoctorReason;

    private Button TakeLeave,CalendarView;
    private EditText name, leaveFrom, leaveTo,leaveReason;
    private String patientRandomKey;
    private DatabaseReference PatientRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_note);

        PatientRef = FirebaseDatabase.getInstance().getReference().child("Doctor Leave");

        TakeLeave = (Button) findViewById(R.id.leave_btn);
        CalendarView = (Button) findViewById(R.id.calendar_view);
        name = (EditText) findViewById(R.id.leave_name);
        leaveFrom = (EditText) findViewById(R.id.leave_from);
        leaveTo = (EditText) findViewById(R.id.leave_to);
        leaveReason = (EditText) findViewById(R.id.leave_reason);
        loadingBar = new ProgressDialog(this);

        CalendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeaveNoteActivity.this,CalendarActivity.class));
            }
        });

        TakeLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateDoctorData();
            }
        });
    }

    private void ValidateDoctorData() {
        DoctorName = String.valueOf(name.getText());
        DoctorFrom = String.valueOf(leaveFrom.getText());
        DoctorTo = String.valueOf(leaveTo.getText());
        DoctorReason = String.valueOf(leaveReason.getText());
        if (TextUtils.isEmpty(DoctorName)){
            Toast.makeText(this, "Please Enter the name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DoctorFrom)){
            Toast.makeText(this, "Please enter the date and month of Leave Start", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DoctorTo)){
            Toast.makeText(this, "Please enter the date and month of Leave End", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DoctorReason)){
            Toast.makeText(this, "Please enter the reason of leave", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreDoctorInformation();
        }

    }

    private void StoreDoctorInformation() {
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
        patientMap.put("name", DoctorName);
        patientMap.put("leaveFrom", DoctorFrom);
        patientMap.put("leaveTo", DoctorTo);
        patientMap.put("leaveReason", DoctorReason);

        PatientRef.child(patientRandomKey).updateChildren(patientMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Intent intent = new Intent(LeaveNoteActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                            loadingBar.dismiss();
                            Toast.makeText(LeaveNoteActivity.this, "Applied for Leave", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(LeaveNoteActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static class LeaveViewHolder extends RecyclerView.ViewHolder{

        public TextView name,phone,reason,from,to,sex;

        public LeaveViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.doctor_name);
            sex = itemView.findViewById(R.id.doctor_sex);
            phone = itemView.findViewById(R.id.doctor_phone_number);
            reason = itemView.findViewById(R.id.doctor_reason_leave);
            from = itemView.findViewById(R.id.doctor_leave_from);
            to = itemView.findViewById(R.id.doctor_leave_to);


        }
    }
}
