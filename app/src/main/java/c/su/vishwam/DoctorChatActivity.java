package c.su.vishwam;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.ViewHolder.ProductViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton SearchButton;
    private EditText SearchInputText;

    private RecyclerView searchResultList;
    private DatabaseReference allUserDatabaseRef;
    String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chat);

        mToolbar = (Toolbar) findViewById(R.id.find_friends_appbar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Chats");

        searchResultList = (RecyclerView) findViewById(R.id.search_result_list);
        searchResultList.setHasFixedSize(true);
        searchResultList.setLayoutManager(new LinearLayoutManager(this));

        SearchButton = findViewById(R.id.search_people_friends_button);
        SearchInputText = findViewById(R.id.search_box_input);

        allUserDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Reception");

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = SearchInputText.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child("Reception");//.child(Prevalent.currentOnlineUser.getPhone());

        FirebaseRecyclerOptions<FindFriends> options =
                new FirebaseRecyclerOptions.Builder<FindFriends>()
                        .setQuery(reference.orderByChild("name").startAt(searchInput),FindFriends.class)
                        .build();

        FirebaseRecyclerAdapter<FindFriends, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<FindFriends, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final FindFriends model) {
                        //holder.patientName.setText(model.getName()+" ["+model.getAge()+", "+model.getGender()+"]");
                        //holder.patientProblem.setText("Issue : " + model.getProblem());
//                         holder.patientTime.setText(model.getTime());
//                         holder.patientDate.setText(model.getDate());
                        holder.chatName.setText(model.getName());
                        holder.chatType.setText(model.getType());
                        Picasso.get().load(model.getImage()).into(holder.chatImage);

                        //holder.patientSex.setText("Se

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(DoctorChatActivity.this, DoctorChatPersonalActivity.class);
                                intent.putExtra("id",model.getId());
                                intent.putExtra("name", model.getName());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_user_display_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        searchResultList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class FindViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;

        public FindViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.all_user_profile_image);
        }
    }
}