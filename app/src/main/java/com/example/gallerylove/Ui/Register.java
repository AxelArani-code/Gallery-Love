package com.example.gallerylove.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gallerylove.R;
import com.example.gallerylove.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    Button btn_Restrado;
    EditText edit_create_email, edir_create_password,edit_sexo,edit_userName;
    ImageView btn_Black;

    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        initializeUI();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_Black:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.btn_Registrar:
                registerNewUser();
                break;
        }
    }

    public void registerNewUser(){
        String userName,email, password, sexo;
        email = edit_create_email.getText().toString().trim();
        password = edir_create_password.getText().toString().trim();
        sexo= edit_sexo.getText().toString().trim();
        userName =  edit_userName.getText().toString().trim();

        if (userName.isEmpty()){
            edit_userName.setError("Ingrese un nombre");
            edit_userName.requestFocus();return;
        }

        if (sexo.isEmpty()){
            edit_sexo.setError("Ingrese su genero");
            edit_sexo.requestFocus();
            return;
        }

        if (email.isEmpty()){
            edit_create_email.setError("Email es erroneo");
            edit_create_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_create_email.setError("Ingrese Email");
            edit_create_email.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edir_create_password.setError("Contraseña incorrecta");
            edir_create_password.requestFocus();
            return;
        }
        if (password.length()<5){
            edir_create_password.setError("Ingrese mas caracteres");
            edir_create_password.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User( userName,email, password, sexo);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "El usuario se ha registrado correctamente", Toast.LENGTH_LONG).show();
                                //mProgressBar.setVisibility(View.GONE);

                                //Redirecto to login Latout
                                //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                ProgressBarStart();

                            }else {
                                Toast.makeText(Register.this,"Error de registro intentalo", Toast.LENGTH_LONG).show();
                                //mProgressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else {
                    Toast.makeText(Register.this,"Error de registro", Toast.LENGTH_LONG).show();
                    //mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }



    private void ProgressBarStart() {
        progressDialog = new ProgressDialog(Register.this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);

        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3500);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                    super.run();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }


    private void initializeUI() {
        btn_Black = findViewById(R.id.btn_Black);
        btn_Restrado= findViewById(R.id.btn_Registrar);
        edir_create_password = findViewById(R.id.edit_Create_password);
        edit_create_email = findViewById(R.id.edit_create_Email);
        edit_sexo= findViewById(R.id.edit_sexo);
        edit_userName = findViewById(R.id.edit_userName);
    }
}