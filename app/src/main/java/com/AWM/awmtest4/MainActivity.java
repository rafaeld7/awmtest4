package com.AWM.awmtest4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

//Registro de usuario
    private EditText TextEmail;
    private EditText TextPassword;
    private Button   btnRegistrar;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
//        //Referenciamos los views
//        TextEmail = (EditText) findViewById(R.id.txtcorreo);
//        TextPassword = (EditText) findViewById(R.id.textPassword);
//        btnRegistrar = (Button) findViewById(R.id.btn_guardar);
//        progressDialog = new ProgressDialog(this);
//        //Boton para escuchar
//      btnRegistrar.setOnClickListener((View.OnClickListener) this);
//        //FIN de resgitro


        setUpView();
        setUpViewPagerAdapter();
    }

//    private  void registrarUsuario(){
//     //Obtenemos el Email y la contrasena desde las cajas de textos
//        String email = TextEmail.getText().toString().trim();
//        String password = TextPassword.getText().toString().trim();
//
//        //verificamos que las cajas de texto no esten vacias
//
//        if(TextUtils.isEmpty(email)){
//            Toast.makeText(this,"Se debe ingresar un Email",Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if(TextUtils.isEmpty(password)){
//            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
//        }
//
//        progressDialog.setMessage("Realizando registro en linea...");
//        progressDialog.show();
//
//
//        //creating a new user
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(MainActivity.this,"Se ha registrado el Email",Toast.LENGTH_LONG).show();
//
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(MainActivity.this,"No se pudo registrar el Usuario",Toast.LENGTH_LONG).show();
//
//                        }
//                        progressDialog.dismiss();
//
//                        // ...
//                    }
//                });
//
//
//
//    }
//      @Override
//    private void OnClick(View view){
//
//        registrarUsuario();
//
//    }


    private void setUpView(){
        imageView = findViewById(R.id.imageView);
        appBarLayout = findViewById(R.id.appBarlayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

    }

    private void setUpViewPagerAdapter() {
        viewPagerAdapter.addfragment(new Opcion1Fragment(),  getString(R.string.tab_1));
        viewPagerAdapter.addfragment(new Opcion2Fragment(), getString(R.string.tab_2));
        viewPagerAdapter.addfragment(new Opcion3Fragment(), getString(R.string.tab_3));
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        imageView.setImageResource(R.drawable.ic_home_black_24dp);
                        Log.d("TAG1","Posicion" + tabLayout.getSelectedTabPosition()+ " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;
                    case 1:
                        imageView.setImageResource(R.drawable.ic_add_circle_outline_black_24dp);
                        Log.d("TAG1","Posicion" + tabLayout.getSelectedTabPosition()+ " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.ic_account_circle_black_24dp);
                        Log.d("TAG1","Posicion" + tabLayout.getSelectedTabPosition()+ " Titulo: " + viewPagerAdapter.getPageTitle(tabLayout.getSelectedTabPosition()));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

   
}
