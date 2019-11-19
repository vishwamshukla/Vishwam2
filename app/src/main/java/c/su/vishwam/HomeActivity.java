package c.su.vishwam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import c.su.vishwam.Model.Patients;
import c.su.vishwam.Prevalent.Prevalent;
import c.su.vishwam.ViewHolder.ProductViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView Name, Problem, OtherDetails, Sex, Age,Time,Date;
    private String Id = "", saveCurrentDate, saveCurrentTime;

    private DatabaseReference PatientsRef,PatientRef1,PatientRef2;
    private RecyclerView recyclerView;
    private String patientRandomKey;
    RecyclerView.LayoutManager layoutManager;
    private ProgressDialog loadingBar;
    private Button completeAppoinButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Id = getIntent().getStringExtra("id");

        loadingBar = new ProgressDialog(this);

        Name = (TextView) findViewById(R.id.patient_name);
        Problem = (TextView) findViewById(R.id.patient_problem);
        OtherDetails = (TextView) findViewById(R.id.patient_other_details);
//        Sex = (TextView) findViewById(R.id.patient_sex);
//        Age = (TextView) findViewById(R.id.patient_age);
        Time = (TextView) findViewById(R.id.patient_time);
        Date = (TextView) findViewById(R.id.patient_date);

        PatientsRef = FirebaseDatabase.getInstance().getReference().child("Patients(OPD)");
        PatientRef1 = FirebaseDatabase.getInstance().getReference().child("Admins").child("8669059504").child("transferRequest");
        PatientRef2 = FirebaseDatabase.getInstance().getReference().child("Admins").child("8669059504").child("handoverRequest");


        Paper.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Appointments");
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

//        userNameTextView.setText(Prevalent.currentOnlineUser.getName());
//        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Patients> options =
                new FirebaseRecyclerOptions.Builder<Patients>()
                .setQuery(PatientsRef, Patients.class)
                .build();

        FirebaseRecyclerAdapter<Patients, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Patients, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Patients model) {
                        holder.patientName.setText(model.getName()+" ["+model.getAge()+", "+model.getGender()+"]");
                        holder.patientPhone.setText(model.getId());
                        //holder.patientSex.setText(model.getSex());
                       // holder.patientProblem.setText("Complaints: "+model.getProblem());
                        holder.patientOthers.setText(model.getComplaints());
                        //holder.patientDate.setText("Date: "+model.getDate());
                        //holder.patientTime.setText("Time: "+model.getTime());
                        //holder.patientAge.setText("Age:"+model.getAge());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(HomeActivity.this, OPDDetailsActivity.class);
                                intent.putExtra("id",model.getId());
                                startActivity(intent);
                            }
                        });
//                        holder.TransferIpd.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                loadingBar.setTitle("Please wait...");
//                                loadingBar.setMessage("Sharing details with the admin department");
//                                loadingBar.setCanceledOnTouchOutside(false);
//                                loadingBar.show();
//
//                                Calendar calendar = Calendar.getInstance();
//                                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd");
//                                saveCurrentDate = currentDate.format(calendar.getTime());
//
//
//                                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm a");
//                                saveCurrentTime = currentTime.format(calendar.getTime());
//
//                                patientRandomKey = saveCurrentDate + "-"+saveCurrentTime;
//
//                                HashMap<String, Object> patientMap = new HashMap<>();
//                                patientMap.put("id", patientRandomKey);
//                                patientMap.put("name",model.getName());
//                                patientMap.put("problem", model.getProblem());
//                                patientMap.put("others", model.getOthers());
//                                patientMap.put("sex",model.getSex());
//                                patientMap.put("age",model.getAge());
//                                patientMap.put("date",saveCurrentDate);
//                                patientMap.put("time",saveCurrentTime);
//                                patientMap.put("phone",model.getPhone());
//
//                                PatientRef1.child(patientRandomKey).updateChildren(patientMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            loadingBar.dismiss();
//                                            Toast.makeText(HomeActivity.this, model.getName()+" patient details shared with the admin department", Toast.LENGTH_SHORT).show();
//
//                                            Intent intent = new Intent(HomeActivity.this, IpdActivity.class);
//                                            //startActivity(intent);
//                                        }
//                                        else {
//                                            Toast.makeText(HomeActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                            }
//                        });
//                        holder.HandoverToDoctor.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                loadingBar.setTitle("Please wait...");
//                                loadingBar.setMessage("Checking available doctors");
//                                loadingBar.setCanceledOnTouchOutside(false);
//                                loadingBar.show();
//
//                                Calendar calendar = Calendar.getInstance();
//                                SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd");
//                                saveCurrentDate = currentDate.format(calendar.getTime());
//
//
//                                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm a");
//                                saveCurrentTime = currentTime.format(calendar.getTime());
//
//                                patientRandomKey = saveCurrentDate + "-"+saveCurrentTime;
//                                HashMap<String, Object> patientMap1 = new HashMap<>();
//                                patientMap1.put("id", patientRandomKey);
//                                patientMap1.put("name",model.getName());
//                                patientMap1.put("problem", model.getProblem());
//                                patientMap1.put("others", model.getOthers());
//                                patientMap1.put("sex",model.getSex());
//                                patientMap1.put("age",model.getAge());
//                                patientMap1.put("date",saveCurrentDate);
//                                patientMap1.put("time",saveCurrentTime);
//                                patientMap1.put("phone",model.getPhone());
//
//                                PatientRef2.child(patientRandomKey).updateChildren(patientMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()){
//                                            loadingBar.dismiss();
//                                            Toast.makeText(HomeActivity.this, "Select doctor", Toast.LENGTH_SHORT).show();
//
//                                            Intent intent = new Intent(HomeActivity.this, IpdActivity.class);
//                                            //startActivity(intent);
//                                        }
//                                        else {
//                                            Toast.makeText(HomeActivity.this, "Error"+task.getException(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//
//                                startActivity(new Intent(HomeActivity.this,DoctorListActivity.class));
//                            }
//                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_layout,parent,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

//    private void sharingDetails(String id) {
//
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm a");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//
//        patientRandomKey = saveCurrentDate + "-"+saveCurrentTime;
//
//        HashMap<String, Object> patientMap = new HashMap<>();
//        patientMap.put("id", patientRandomKey);
//        patientMap.put("name",);
//    }

    private void addingToIpd() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference IpdListRef = FirebaseDatabase.getInstance().getReference().child("Patient IPD");

        final HashMap<String, Object> ipdList = new HashMap<>();
        ipdList.put("id",Id);
        ipdList.put("name",Name.getText().toString());
        ipdList.put("problem",Problem.getText().toString());
        ipdList.put("others",OtherDetails.getText().toString());
        ipdList.put("sex",Sex.getText().toString());
        ipdList.put("age",Age.getText().toString());

        IpdListRef.child(Prevalent.currentOnlineUser.getPhone())
                .child(Id)
                .updateChildren(ipdList)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            IpdListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Patients").child(Id)
                                    .updateChildren(ipdList)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(HomeActivity.this, "Patient added!", Toast.LENGTH_SHORT).show();
                                            }                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.main_find_patients_option) {
            startActivity(new Intent(HomeActivity.this,SearchActivity.class));
        }
        else if (id == R.id.history){
            startActivity(new Intent(HomeActivity.this,HistoryActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_opd) {
            startActivity(new Intent(HomeActivity.this,OpdActivity.class));
        } else if (id == R.id.nav_ipd) {
            Intent intent = new Intent(HomeActivity.this,IpdActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_emergency) {
            Intent intent = new Intent(HomeActivity.this,EmergencyViewActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_leave_note) {
            startActivity(new Intent(HomeActivity.this,LeaveNoteActivity.class));

        } else if (id == R.id.nav_chat) {
            Intent intent = new Intent(HomeActivity.this,DoctorChatActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_settings){
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_contact){
            Intent intent = new Intent(HomeActivity.this, ContactUsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_about_app){
            Intent intent = new Intent(HomeActivity.this, ReceptionActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class IpdTransferViewHolder extends RecyclerView.ViewHolder{

        public TextView name,phone,others,problem,age,sex,time,date,bed,ward,to;

        public IpdTransferViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.pat_name);
            sex = itemView.findViewById(R.id.pat_sex);
            phone = itemView.findViewById(R.id.pat_phone_number);
            age = itemView.findViewById(R.id.pat_age);
            time = itemView.findViewById(R.id.pat_time);
            date = itemView.findViewById(R.id.pat_date);
            problem = itemView.findViewById(R.id.pat_problem);
            others = itemView.findViewById(R.id.pat_other_details);
            bed = itemView.findViewById(R.id.patient1_bed_no);
            ward = itemView.findViewById(R.id.patient1_ward_no);
            to = itemView.findViewById(R.id.patient_doctor_to);

        }
    }
}
