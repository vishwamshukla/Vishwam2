package c.su.vishwam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import c.su.vishwam.Model.IpdPatients;
import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    private DatabaseReference patientRef;
    private String Id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = (ImageView) findViewById(R.id.emergency_image);

        Id = getIntent().getStringExtra("id");
        getPatientDetails(Id);



    }

    private void getPatientDetails(String id) {

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Patients(Emergency)").child(Prevalent.currentOnlineUser.getPhone());


        productsRef.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Patients patients = dataSnapshot.getValue(Patients.class);
                    //imageView.ima;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}