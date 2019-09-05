package c.su.vishwam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorForgotPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button Continue;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.forgot_email);
        Continue = (Button) findViewById(R.id.forgot_button);

        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                if (TextUtils.isEmpty(userEmail)){
                    Toast.makeText(DoctorForgotPasswordActivity.this, "Email required", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(DoctorForgotPasswordActivity.this, "Please check your email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DoctorForgotPasswordActivity.this, DoctorLoginActivity.class));
                            }
                            else {
                                String message = task.getException().getMessage();
                                Toast.makeText(DoctorForgotPasswordActivity.this, "Error occured"+ message , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
