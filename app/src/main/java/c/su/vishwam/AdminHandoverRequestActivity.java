package c.su.vishwam;

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
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import c.su.vishwam.Model.IpdPatients;

public class AdminHandoverRequestActivity extends AppCompatActivity {

    private RecyclerView ipdList;
    private DatabaseReference patientRef;
    private TextView closeTextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_handover_request);

        patientRef = FirebaseDatabase.getInstance().getReference().child("Admins").child("8669059504").child("handoverRequest");


        ipdList = findViewById(R.id.opd_list_recycler_view1);
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

//        FirebaseRecyclerOptions<IpdPatients> options =
//                new FirebaseRecyclerOptions.Builder<IpdPatients>()
//                        .setQuery(patientRef,IpdPatients.class)
//                        .build();
//        FirebaseRecyclerAdapter<IpdPatients, HomeActivity.IpdTransferViewHolder> adapter =
//                new FirebaseRecyclerAdapter<IpdPatients, HomeActivity.IpdTransferViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull HomeActivity.IpdTransferViewHolder holder, int position, @NonNull IpdPatients model) {
//                        holder.name.setText("Name: "+model.getName());
//                        holder.phone.setText("Phone: "+model.getPhone());
//                        holder.sex.setText("Sex: "+model.getSex());
//                        holder.problem.setText("Issue: "+model.getProblem());
//                        holder.others.setText("Details: "+model.getOthers());
//                        holder.time.setText("Time: "+model.getTime());
//                        holder.date.setText("Date: "+model.getDate());
//                        holder.age.setText("Age: "+model.getAge());
//                        holder.to.setText("To: "+model.getTo());
//
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public HomeActivity.IpdTransferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.handover_layout,parent,false);
//                        return new HomeActivity.IpdTransferViewHolder(view);
//                    }
//                };
//        ipdList.setAdapter(adapter);
//        adapter.startListening();
    }
}
