package c.su.vishwam;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import c.su.vishwam.Model.Users;
import c.su.vishwam.Prevalent.Prevalent;

public class AdminLoginActivity extends AppCompatActivity {


    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton,LoginwithPhone;
    private ProgressDialog loadingBar;
    private String parentDbName = "Admins";
    //private android.widget.CheckBox chkBoxRememberMe;
    private ImageView circle1;
    TextView tvLogin;

    // private TextView ForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        LoginButton = (Button) findViewById(R.id.login_btn_admin);
        LoginwithPhone = (Button) findViewById(R.id.login_with_phone_admin);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input_admin);
        InputPassword = (EditText) findViewById(R.id.login_password_input_admin);
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


    }
    private void LoginUser() {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Enter Phone Number!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Password!", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Logging in....");
            loadingBar.setMessage("Please wait, checking credentials...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

//        if (chkBoxRememberMe.isChecked()){
//            Paper.book().write(Prevalent.UserPhoneKey,phone);
//            Paper.book().write(Prevalent.UserPasswordKey,password);
//        }


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(phone).exists()){
                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (usersData.getPhone().equals(phone)){
                        if (usersData.getPassword().equals(password)){
//                            if (parentDbName.equals("Admins")){
//                                Toast.makeText(LoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
//                                loadingBar.dismiss();
//
//                                Intent intent = new Intent(LoginActivity.this,AdminCategoryActivity.class);
//                                startActivity(intent);
//                            }
                            if (parentDbName.equals("Admins")){
                                Toast.makeText(AdminLoginActivity.this, "Logged in!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(AdminLoginActivity.this,AdminHomeActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(AdminLoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(AdminLoginActivity.this, "Account with this "+phone+" number do not exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
