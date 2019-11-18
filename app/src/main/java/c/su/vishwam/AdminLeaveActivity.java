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
import c.su.vishwam.Model.Users;

public class AdminLeaveActivity extends AppCompatActivity {
    private RecyclerView ipdList;
    private DatabaseReference patientRef;
    private TextView closeTextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_leave);

        patientRef = FirebaseDatabase.getInstance().getReference().child("Doctor Leave");


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
//        FirebaseRecyclerOptions<Users> options =
//                new FirebaseRecyclerOptions.Builder<Users>()
//                        .setQuery(patientRef,Users.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<Users, LeaveNoteActivity.LeaveViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Users, LeaveNoteActivity.LeaveViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull LeaveNoteActivity.LeaveViewHolder holder, int position, @NonNull Users model) {
//                        holder.name.setText("Name: "+model.getName());
//                        holder.phone.setText("Phone: "+model.getPhone());
//                        holder.sex.setText("Sex: "+model.getSex());
//                        holder.reason.setText("Reason: "+model.getLeaveReason());
//                        holder.from.setText("From: "+model.getLeaveFrom());
//                        holder.to.setText("To: "+model.getLeaveTo());
//                    }
//
//                    @NonNull
//                    @Override
//                    public LeaveNoteActivity.LeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_leave_layout,parent,false);
//                        return new LeaveNoteActivity.LeaveViewHolder(view);
//                    }
//                };
//        ipdList.setAdapter(adapter);
//        adapter.startListening();
    }

    public static class IpdViewHolder extends RecyclerView.ViewHolder{

        public TextView name,phone,reason,sex,from,to;

        public IpdViewHolder(@NonNull View itemView) {
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
