package com.AWM.awmtest4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroFirebase2Activity extends AppCompatActivity implements View.OnClickListener {

    //Registro de usuario
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    // private Button btn_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_firebase2);

        //Cancelar
        final Button btn_cancelar = (Button) findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Referenciamos los views
       TextEmail = (EditText)findViewById(R.id.txtcorreo);
       TextPassword = (EditText)findViewById(R.id.textPassword);

        btnRegistrar = (Button) findViewById(R.id.btn_guardar);
        progressDialog = new ProgressDialog(this);
        //Boton para escuchar
        btnRegistrar.setOnClickListener(this);
        //FIN de resgitro


    }

//
    private  void registrarUsuario(){
        //Obtenemos el Email y la contrasena desde las cajas de texto
         String email = TextEmail.getText().toString().trim();
         String password = TextPassword.getText().toString().trim();

        //verificamos que las cajas de texto no esten vacias

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un Email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();


        //creating a new user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Sign in success, update UI with the signed-in user's information
                        if (task.isSuccessful())
                            Toast.makeText(RegistroFirebase2Activity.this, "Se ha registrado el Email", Toast.LENGTH_LONG).show();
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistroFirebase2Activity.this,"No se pudo registrar el Usuario",Toast.LENGTH_LONG).show();

                        }
                        progressDialog.dismiss();

                    }
                });

    }

    @Override
    public void onClick(View view) {

        registrarUsuario();

    }
}
