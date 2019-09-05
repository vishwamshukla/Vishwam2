package c.su.vishwam;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import c.su.vishwam.Model.Users;
import c.su.vishwam.Prevalent.Prevalent;
import io.paperdb.Paper;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText InputEmail, InputPassword;
    private TextView forgotPassword;
    private Button LoginButton, LoginwithPhone;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    //private android.widget.CheckBox chkBoxRememberMe;
    private ImageView circle1;
    TextView tvLogin;
    private ImageButton btRegister;
    private FirebaseAuth mAuth;

    // private TextView ForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        mAuth = FirebaseAuth.getInstance();

        btRegister = findViewById(R.id.btRegister);

        forgotPassword = (TextView) findViewById(R.id.tvForgot);

        LoginButton = (Button) findViewById(R.id.login_btn_doctor);
        LoginwithPhone = (Button) findViewById(R.id.login_with_phone_doctor);
        InputEmail = (EditText) findViewById(R.id.login_email_input_doctor);
        InputPassword = (EditText) findViewById(R.id.login_password_input_doctor);
        loadingBar = new ProgressDialog(this);
        //chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb_reception);
        //Paper.init(this);

        //ForgotPassword = (TextView) findViewById(R.id.forgot_password_link_reception);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
//                if (v==btRegister){
//                    Intent a = new Intent(DoctorLoginActivity.this, DoctorRegisterActivity.class);
//                    Pair[] pairs = new Pair[1];
//                    pairs[0] = new Pair<View,String> (tvLogin,"login");
//                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(DoctorLoginActivity.this,pairs);
//                    startActivity(a,activityOptions.toBundle());
//                }
                startActivity(new Intent(DoctorLoginActivity.this, DoctorRegisterActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorLoginActivity.this, DoctorForgotPasswordActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser ==  null){
            SendUserToHomeActivity();
        }
    }

    private void LoginUser() {
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter Phone Number!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login in progress");
            loadingBar.setMessage("Please wait...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SendUserToHomeActivity();
                                Toast.makeText(DoctorLoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(DoctorLoginActivity.this, "Error-" + message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void SendUserToHomeActivity() {
        Intent intent = new Intent(DoctorLoginActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

//    private void AllowAccessToAccount(final String email, final String password) {
//
////        if (chkBoxRememberMe.isChecked()){
////            Paper.book().write(Prevalent.UserPhoneKey,phone);
////            Paper.book().write(Prevalent.UserPasswordKey,password);
////        }
//
//
//        final DatabaseReference RootRef;
//        RootRef = FirebaseDatabase.getInstance().getReference();
//
//        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.child(parentDbName).child(phone).exists()){
//                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
//                    if (usersData.getPhone().equals(phone)){
//                        if (usersData.getPassword().equals(password)){
////                            if (parentDbName.equals("Admins")){
////                                Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
////                                loadingBar.dismiss();
////
////                                Intent intent = new Intent(LoginActivity.this,AdminCategoryActivity.class);
////                                startActivity(intent);
////                            }
//                            if (parentDbName.equals("Users")){
//                                Toast.makeText(DoctorLoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
//                                loadingBar.dismiss();
//
//                                Intent intent = new Intent(DoctorLoginActivity.this,HomeActivity.class);
//                                Prevalent.currentOnlineUser = usersData;
//                                startActivity(intent);
//                            }                        }
//                        else {
//                            loadingBar.dismiss();
//                            Toast.makeText(DoctorLoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//                else {
//                    Toast.makeText(DoctorLoginActivity.this, "Account with this "+phone+" number do not exists", Toast.LENGTH_SHORT).show();
//                    loadingBar.dismiss();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//}
