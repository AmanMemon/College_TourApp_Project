package com.example.loginpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registerpage extends AppCompatActivity {
    TextView Alreadyhaveaccount;
    EditText inputemail,inputpassword,inputconformpassword;
    Button btnregister;
    String emailpattern = "[a-z0-9._-]+@.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        inputemail = findViewById(R.id.Email);
        inputpassword = findViewById(R.id.password);
        inputconformpassword = findViewById(R.id.cpassword);
        btnregister = findViewById(R.id.registerUser);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Alreadyhaveaccount = findViewById(R.id.haccount);


        Alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( Registerpage.this, MainActivity.class ));
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }

            private void PerforAuth() {
                String email = inputemail.getText().toString();
                String password = inputpassword.getText().toString();
                String confirmpassword = inputconformpassword.getText().toString();

            if (!email.matches(emailpattern)){
                inputemail.setError("Enter correct Email");
            }else if(password.isEmpty() || password.length()<6)
            {
                inputpassword.setError("Enter Proper Password");
            }else if (!password.equals(confirmpassword))
            {
                inputconformpassword.setError("Password Not Match Both Field");
            }else
            {
                progressDialog.setMessage("Please Wait While Registration...");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            sendUserToNextActivity();
                            Toast.makeText(Registerpage.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(Registerpage.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    private void sendUserToNextActivity() {
                        Intent intent=new Intent(Registerpage.this,HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
            }
        });
    }
}