package c.su.vishwam;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import c.su.vishwam.Model.EmergencyPatients;
import c.su.vishwam.Model.IpdPatients;
import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;
import c.su.vishwam.ViewHolder.ProductViewHolder;

public class EmergencyActivity extends AppCompatActivity {

    private RecyclerView ipdList;
    private DatabaseReference patientRef;
    private TextView  closeTextBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        patientRef = FirebaseDatabase.getInstance().getReference().child("Patients(Emergency)").child(Prevalent.currentOnlineUser.getPhone());

        ipdList = findViewById(R.id.emergency_list_recycler_view);
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
        FirebaseRecyclerAdapter<IpdPatients, IpdActivity.IpdViewHolder> adapter =
                new FirebaseRecyclerAdapter<IpdPatients, IpdActivity.IpdViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull IpdActivity.IpdViewHolder holder, int position, @NonNull IpdPatients model) {
                        holder.name.setText("Name: "+model.getName());
                        holder.phone.setText("Phone: "+model.getPhone());
                        holder.sex.setText("Sex: "+model.getSex());
                        holder.problem.setText("Issue: "+model.getProblem());
                        holder.others.setText("Details: "+model.getOthers());
                        holder.time.setText("Time: "+model.getTime());
                        holder.date.setText("Date: "+model.getDate());
                        //Picasso.get().load(model.getImage()).into(holder.);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //startActivity(new Intent(EmergencyActivity.this,ImageActivity.class));
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public IpdActivity.IpdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emrgency_layout1,parent,false);
                        return new IpdActivity.IpdViewHolder(view);
                    }
                };
        ipdList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class IpdViewHolder extends RecyclerView.ViewHolder{

        public TextView name,phone,problem,others,time,sex;
        public ImageView EmergencyImage;
        public Button crossConsult, discharge;

        public IpdViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.patient_name);
            sex = itemView.findViewById(R.id.patient_sex);
            phone = itemView.findViewById(R.id.patient_phone_number);
            problem = itemView.findViewById(R.id.patient_problem);
            others = itemView.findViewById(R.id.patient_other_details);
            time = itemView.findViewById(R.id.patient_date_time);
        }
    }
}
