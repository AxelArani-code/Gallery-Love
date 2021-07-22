package com.example.gallerylove.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gallerylove.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    //UI
    Button btn_inicio;
    EditText edit_Email, edit_contraseña;
    TextView btn_Crear_Cuenta;
    //Recursos
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    Animation scaleUp, scaleDown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        initializeUI();

    }




    private void loginUseAccount(){
        String email, password;
        email = edit_Email.getText().toString().trim();
        password = edit_contraseña.getText().toString().trim();

        if (email.isEmpty()){
            edit_Email.setError("Email es incorrecto");
            edit_Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_Email.setError("Porfavor ingrese su email");
            edit_Email.requestFocus();
            return;
        }
        if (password.isEmpty()){
            edit_contraseña.setError("Contraseñas incorrecta");
            edit_contraseña.requestFocus();
            return;
        }
        if (password.length()<5){
            edit_contraseña.setError("Ingrese mas caracteres");
            edit_contraseña.requestFocus();
            return;
        }

    mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText(Login.this, "Inicio de sesión exitoso!!", Toast.LENGTH_LONG).show();
                        //progressBa.setVisibility(View.GONE);
                        //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        ProgressBarStart_Login();
                    }
                    else {
                        Toast.makeText(Login.this, "¡Error de inicio de sesion! Por favor, inténtelo de nuevo más tarde"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        //progressBa.setVisibility(View.GONE);

                    }
                }
            });
}


    public void onClick(View view){
    switch (view.getId()) {
        case R.id.btn_Crear_cuenta:
            ProgressBarStart_Register();
            break;
        case R.id.btn_iniciar :
            loginUseAccount();
            break;

        }
    }



    private void ProgressBarStart_Login() {
        progressDialog = new ProgressDialog(Login.this);

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

    private void ProgressBarStart_Register() {
        progressDialog = new ProgressDialog(Login.this);

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
                    Intent intent = new Intent(getApplicationContext(), Register.class);
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
    btn_inicio = findViewById(R.id.btn_iniciar);
    edit_contraseña = findViewById(R.id.Edit_constaseña);
    edit_Email= findViewById(R.id.edit_email);
    btn_Crear_Cuenta = findViewById(R.id.btn_Crear_cuenta);
    }
}