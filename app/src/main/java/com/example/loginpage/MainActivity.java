package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView createanaccount;
    EditText inputemail,inputpassword;
    Button btnlogin;
    String emailpattern = "[a-z0-9._-]+@.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;    FirebaseUser mUser;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createanaccount = findViewById(R.id.caccount);


        inputemail = findViewById(R.id.Email);
        inputpassword = findViewById(R.id.Password);
        btnlogin = findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        createanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registerpage.class);
                startActivity(intent);

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performlogin();
            }

            private void performlogin() {
                String email = inputemail.getText().toString();
                String password = inputpassword.getText().toString();

                if (!email.matches(emailpattern)) {
                    inputemail.setError("Enter correct Email");
                } else if (password.isEmpty() || password.length() < 6) {
                    inputpassword.setError("Enter Proper Password");
                } else {
                    progressDialog.setMessage("Please Wait While Login...");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                sendUserToNextActivity();
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        private void sendUserToNextActivity() {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }
}
