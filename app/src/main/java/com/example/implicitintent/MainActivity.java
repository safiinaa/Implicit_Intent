package com.example.implicitintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gso;
    Button btnDialpad, btnContact, btnCamera, btnGalery, btnBrowser, btnGoogle;
    EditText etHp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDialpad = findViewById(R.id.btnDialpad);
        btnContact = findViewById(R.id.btnContact);
        btnCamera = findViewById(R.id.btnCamera);
        btnGalery = findViewById(R.id.btnGalery);
        btnBrowser = findViewById(R.id.btnBrowser);
        btnGoogle = findViewById(R.id.btnGoogle);
        etHp = findViewById(R.id.etHp);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gso = GoogleSignIn.getClient(this,gso);

        btnDialpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etHp.getText().toString().isEmpty()){
                    //Tanpa Mengirimkan Data
                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_DIAL);
                    startActivity(i);
                } else {
                    //Mengirimkan Data Nomer Telephone
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + etHp.getText()));
                    startActivity(intent);
                }
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
                startActivity(intent);
            }
        });

        btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("content://media/external/images/media/"));
                startActivity(intent);
            }
        });

        btnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://amikom.ac.id/"));
                startActivity(Intent.createChooser(intent, "title"));
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        void signIn(){
            Intent signInIntent = gso.getSignInIntent();
            startActivityForResult(signInIntent, 1000);
        }

        @Override
        protected void onActivityReselt(int requestCode, int resultCode,Intent data)
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1000){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                try {
                    task.getResult(ApiException.class);
                    navigateToSecondActivity();
                } catch (ApiException e) {
                    throw new RuntimeException(e);
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
    }

    void
}