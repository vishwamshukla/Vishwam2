package c.su.vishwam;

import android.content.Context;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.Model.Users;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorChatPersonalActivity extends AppCompatActivity {

    private Toolbar ChattoolBar;
    private ImageButton SendMessageButton, SendImageFileButton;
    private EditText userMessageInput;
    private RecyclerView userMessageList;

    String messageReceiverID="", messageRecieverName, Id = "";
    private TextView receiverName;
    private CircleImageView recieverProfileImage;

    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chat_personal);

        RootRef = FirebaseDatabase.getInstance().getReference();

//        Id = getIntent().getStringExtra("id");
//        getPatientDetails(messageReceiverID);

        ChattoolBar = findViewById(R.id.chat_bar_layout);
        setSupportActionBar(ChattoolBar);

        SendMessageButton = findViewById(R.id.send_message_button);
        SendImageFileButton = findViewById(R.id.send_image_file);
        userMessageInput = findViewById(R.id.input_message);

        messageReceiverID = getIntent().getStringExtra("id");
        messageRecieverName = getIntent().getStringExtra("name");

        receiverName = findViewById(R.id.custom_profile_name);
        recieverProfileImage = findViewById(R.id.custom_profile_image);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = layoutInflater.inflate(R.layout.chat_custom_bar, null);
        actionBar.setCustomView(action_bar_view);

        DisplayReceieverInfo();
        //patientImageDisplay(recieverProfileImage);
    }

    private void getPatientDetails(String id) {
        receiverName.setText(messageRecieverName);
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Reception");


//        productsRef.child(messageReceiverID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    Users findFriends = dataSnapshot.getValue(Users.class);
//
//                    receiverName.setText();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
    private void patientImageDisplay(final CircleImageView recieverProfileImage) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Reception").child(messageReceiverID);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("image").exists())
                    {
                        //progressBar.setVisibility(View.INVISIBLE);
                        String image = String.valueOf(dataSnapshot.child("image").getValue());

                        Picasso.get().load(image).into(recieverProfileImage);

                    }

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }


    private void DisplayReceieverInfo() {
        receiverName.setText(messageRecieverName);
        RootRef.child("Users").child("Reception").child(messageReceiverID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    final String profileImage = dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(profileImage).into(recieverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
