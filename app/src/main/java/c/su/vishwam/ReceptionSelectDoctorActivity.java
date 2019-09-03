package c.su.vishwam;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.ViewHolder.ProductViewHolder;

public class ReceptionSelectDoctorActivity extends AppCompatActivity {


    private TextView Name, Problem, OtherDetails, Sex, Age,Time,Date;
    private String Id = "", saveCurrentDate, saveCurrentTime;

    private DatabaseReference PatientsRef,PatientRef1,PatientRef2;
    private RecyclerView recyclerView;
    private String patientRandomKey;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception_select_doctor);

        Id = getIntent().getStringExtra("id");

        loadingBar = new ProgressDialog(this);

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Patients> options =
                new FirebaseRecyclerOptions.Builder<Patients>()
                        .setQuery(PatientsRef, Patients.class)
                        .build();

        FirebaseRecyclerAdapter<Patients, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Patients, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Patients model) {
                        holder.patientName.setText("Name: "+model.getName());
                        holder.patientPhone.setText("Phone: "+model.getPhone());
                        holder.patientSex.setText("Sex: "+model.getSex());
                        holder.patientProblem.setText("Issue: "+model.getProblem());
                        holder.patientOthers.setText("Details: "+model.getOthers());
                        holder.patientDate.setText("Date: "+model.getDate());
                        holder.patientTime.setText("Time: "+model.getTime());
                        holder.patientAge.setText("Age:"+model.getAge());
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(HomeActivity.this, PatientDetailsActivity.class);
//                                intent.putExtra("id",model.getId());
//                                startActivity(intent);
//                            }
//                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
