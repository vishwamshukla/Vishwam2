package c.su.vishwam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import c.su.vishwam.Model.IpdPatients;
import c.su.vishwam.Prevalent.Prevalent;

public class IpdActivity extends AppCompatActivity {

    private RecyclerView ipdList;
    private DatabaseReference patientRef,PatientRef1;
    private TextView  closeTextBtn;
    public Button crossConsult, discharge;
    private String patientRandomKey,saveCurrentDate, saveCurrentTime;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipd);

        patientRef = FirebaseDatabase.getInstance().getReference().child("Patient IPD").child(Prevalent.currentOnlineUser.getPhone());
        PatientRef1 = FirebaseDatabase.getInstance().getReference().child("Admins").child("8669059504").child("dischargeRequest");

        ipdList = findViewById(R.id.ipd_list_recycler_view);
        loadingBar = new ProgressDialog(this);
        ipdList.setLayoutManager(new LinearLayoutManager(this));
        closeTextBtn = (TextView) findViewById(R.id.close_settings_btn);



        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<IpdPatients> options =
                new FirebaseRecyclerOptions.Builder<IpdPatients>()
                .setQuery(patientRef,IpdPatients.class)
                .build();
        FirebaseRecyclerAdapter<IpdPatients, IpdViewHolder> adapter =
                new FirebaseRecyclerAdapter<IpdPatients, IpdViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull IpdViewHolder holder, final int position, @NonNull final IpdPatients model) {
                        holder.name.setText("Name: "+model.getName());
                        holder.phone.setText("Phone: "+model.getPhone());
                        holder.sex.setText("Sex: "+model.getSex());
                        holder.problem.setText("Issue: "+model.getProblem());
                        holder.others.setText("Details: "+model.getOthers());
                        holder.time.setText("Admission Time: "+model.getTime());
                        holder.date.setText("Admission Date: "+model.getDate());
                        holder.ward.setText("Ward: "+model.getWard());
                        holder.bed.setText("Bed: "+model.getBed());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(IpdActivity.this,IpdDetailsActivity.class);
                                intent.putExtra("id",model.getId());
                                startActivity(intent);
                            }
                        });

                        holder.crossConsult.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(IpdActivity.this,DoctorListActivity.class));
                            }
                        });
                       holder.discharge.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               loadingBar.setTitle("Please wait...");
                               loadingBar.setMessage("Sharing details with the admin department");
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
                               patientMap.put("name",model.getName());
                               patientMap.put("problem", model.getProblem());
                               patientMap.put("others", model.getOthers());
                               patientMap.put("sex",model.getSex());
                               patientMap.put("age",model.getAge());
                               patientMap.put("date",saveCurrentDate);
                               patientMap.put("time",saveCurrentTime);
                               patientMap.put("phone",model.getPhone());
                               patientMap.put("ward",model.getWard());
                               patientMap.put("bed",model.getBed());

                               PatientRef1.child(patientRandomKey).updateChildren(patientMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if (task.isSuccessful()){
                                            loadingBar.dismiss();
                                           Toast.makeText(IpdActivity.this, "Discharge request of "+model.getName()+" sent to the admin department", Toast.LENGTH_SHORT).show();
                                           Intent intent = new Intent(IpdActivity.this, IpdActivity.class);
                                           //startActivity(intent);
                                       }
                                       else {
                                           Toast.makeText(IpdActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               });

                           }
                       });

                    }

                    @NonNull
                    @Override
                    public IpdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ipd_layout,parent,false);
                        return new IpdViewHolder(view);
                    }
                };
        ipdList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class IpdViewHolder extends RecyclerView.ViewHolder{

        public TextView name,phone,problem,others,time,sex,date,ward,bed,age,fromDoc;
        public Button crossConsult, discharge;

        public IpdViewHolder(@NonNull View itemView) {
            super(itemView);

            age = itemView.findViewById(R.id.patient_age1);
            name = itemView.findViewById(R.id.patient_name);
            sex = itemView.findViewById(R.id.patient_sex);
            phone = itemView.findViewById(R.id.patient_phone_number);
            problem = itemView.findViewById(R.id.patient_problem);
            others = itemView.findViewById(R.id.patient_other_details);
            time = itemView.findViewById(R.id.patient_date_time);
            date = itemView.findViewById(R.id.patient_date);
            ward = itemView.findViewById(R.id.patient_ward_no);
            bed = itemView.findViewById(R.id.patient_bed_no);
            crossConsult = (Button) itemView.findViewById(R.id.cross_consult);
            discharge = (Button) itemView.findViewById(R.id.discharge);
            fromDoc = itemView.findViewById(R.id.patient_doctor);

        }
    }
}
