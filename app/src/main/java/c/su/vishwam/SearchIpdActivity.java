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

import c.su.vishwam.Model.IpdPatients;
import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;
import c.su.vishwam.ViewHolder.ProductViewHolder;

public class SearchIpdActivity extends AppCompatActivity {

    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ipd);

        inputText = (EditText) findViewById(R.id.search_patient_name);
        searchBtn = (Button) findViewById(R.id.search);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchIpdActivity.this));

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

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Patients(IPD)").child(Prevalent.currentOnlineUser.getPhone());

        FirebaseRecyclerOptions<IpdPatients> options =
                new FirebaseRecyclerOptions.Builder<IpdPatients>()
                        .setQuery(reference.orderByChild("name").startAt(SearchInput),IpdPatients.class)
                        .build();

        FirebaseRecyclerAdapter<IpdPatients, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<IpdPatients, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final IpdPatients model) {
                        holder.patientName.setText("Name : " + model.getName());
                        holder.patientProblem.setText("Issue : " + model.getProblem());
                        holder.patientTime.setText("Time : "+model.getTime());
                        holder.patientDate.setText("Date : "+model.getDate());
                        holder.patientOthers.setText("Details : "+model.getOthers());
                        holder.patientSex.setText("Sex : "+model.getSex());
                        holder.patientPhone.setText("Phone : "+model.getPhone());
                        holder.bed.setText("Bed : "+model.getBed());
                        holder.ward.setText("Ward : "+model.getWard());

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(SearchIpdActivity.this, PatientDetailsActivity.class);
                                intent.putExtra("id",model.getId());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ipd_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }


}
