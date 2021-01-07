package com.example.passingintent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private ImageView dp;
    private TextView name, email, gender;
    TextView textBox,textview1;

    private static final int REQUEST_CODE_LOGIN = 10;
    ImageView userdetails;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().hide();
        dp = (ImageView) findViewById(R.id.dp);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        textBox = (TextView)findViewById(R.id.address);



        Intent i = getIntent();
        final String i_name, i_email, i_gender, i_url;
        i_name = i.getStringExtra("p_name");
        i_email = i.getStringExtra("p_email");
        i_url = i.getStringExtra("p_url");

        name.setText(i_name);
        email.setText(i_email);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(i_url);
                    InputStream is = url.openConnection().getInputStream();
                    final Bitmap bmp = BitmapFactory.decodeStream(is);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dp.setImageBitmap(bmp);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    public boolean changephone(View view) {

        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        intent.putExtra("weaponTitle","Genuine Freedom Staff");
        startActivity(intent);
        finish();
        return true;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();

            try {
                Intent sendData = new Intent(MainActivity2.this, MainActivity2.class);
                String name, email, dpUrl = "";
                name = account.getDisplayName();
                email = account.getEmail();

                dpUrl = account.getPhotoUrl().toString();
                sendData.putExtra("p_name", name);
                sendData.putExtra("p_email", email);
                sendData.putExtra("p_url", dpUrl);

                startActivity(sendData);

            } catch (Exception e) {
                Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity2.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
