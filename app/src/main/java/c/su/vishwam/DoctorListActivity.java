package c.su.vishwam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import c.su.vishwam.Model.Users;


public class DoctorListActivity extends AppCompatActivity {

    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_groups = new ArrayList<>();
    ArrayList name = new ArrayList<String>();
    ArrayList keys = new ArrayList<String>();
    ArrayList emails = new ArrayList<String>();

    private DatabaseReference DoctorRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Username");

        list_view = (ListView) findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(DoctorListActivity.this, android.R.layout.simple_list_item_1,list_of_groups);
        list_view.setAdapter(arrayAdapter);

        RetrieveAndDisplayDoctors();
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                String cureentGroupName = adapterView.getItemAtPosition(position).toString();

                Toast.makeText(DoctorListActivity.this, "Patient details shared with selected doctor "+cureentGroupName, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void RetrieveAndDisplayDoctors() {
        DoctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext()){
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }
                list_of_groups.clear();
                list_of_groups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    }
