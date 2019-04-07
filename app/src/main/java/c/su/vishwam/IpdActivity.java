package c.su.vishwam;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import c.su.vishwam.Model.IpdPatients;
import c.su.vishwam.Prevalent.Prevalent;

public class IpdActivity extends AppCompatActivity {

    private RecyclerView ipdList;
    private DatabaseReference patientRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipd);

        patientRef = FirebaseDatabase.getInstance().getReference().child("Patient IPD List").child("Doctor View").child(Prevalent.currentOnlineUser.getPhone()).child("Patients");

        ipdList = findViewById(R.id.ipd_list_recycler_view);
        ipdList.setLayoutManager(new LinearLayoutManager(this));
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
                    protected void onBindViewHolder(@NonNull IpdViewHolder holder, int position, @NonNull IpdPatients model) {
                        holder.name.setText("Name: "+model.getName());
                        holder.phone.setText("Phone: "+model.getPhone());
                        holder.sex.setText("Sex: "+model.getSex());
                        holder.problem.setText("Issue: "+model.getProblem());
                        holder.others.setText("Details: "+model.getOthers());
                        holder.time.setText("Admission: "+model.getDate());

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

        public TextView name,phone,problem,others,time,sex;
        public Button crossConsult, discharge;

        public IpdViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.patient_name);
            sex = itemView.findViewById(R.id.patient_sex);
            phone = itemView.findViewById(R.id.patient_phone_number);
            problem = itemView.findViewById(R.id.patient_problem);
            others = itemView.findViewById(R.id.patient_other_details);
            time = itemView.findViewById(R.id.patient_date_time);
            crossConsult = (Button) itemView.findViewById(R.id.cross_consult);
            discharge = (Button) itemView.findViewById(R.id.discharge);
        }
    }
}
