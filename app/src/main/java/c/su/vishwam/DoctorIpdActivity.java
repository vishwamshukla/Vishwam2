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
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.ViewHolder.ProductViewHolder;

public class DoctorIpdActivity extends AppCompatActivity {

    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        inputText = (EditText) findViewById(R.id.search_patient_name);
        searchBtn = (Button) findViewById(R.id.search);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(DoctorIpdActivity.this));

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchInput = inputText.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patients(IPD)");//.child(Prevalent.currentOnlineUser.getPhone());

        FirebaseRecyclerOptions<Patients> options =
                new FirebaseRecyclerOptions.Builder<Patients>()
                        .setQuery(reference.orderByChild("name").startAt(SearchInput),Patients.class)
                        .build();

        FirebaseRecyclerAdapter<Patients, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Patients, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Patients model) {
                        holder.patientName.setText(model.getName()+" ["+model.getAge()+", "+model.getGender()+", "+model.getWardbed()+"]");
                        //holder.patientProblem.setText("Issue : " + model.getProblem());
//                         holder.patientTime.setText(model.getTime());
//                         holder.patientDate.setText(model.getDate());
                        holder.patientOthers.setText(model.getComplaints());
                        //holder.patientSex.setText("Sex : "+model.getSex());
                        holder.patientPhone.setText(model.getId());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(DoctorIpdActivity.this, DoctorIpdDetailActivity.class);
                                intent.putExtra("id",model.getId());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}
