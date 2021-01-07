package com.example.passingintent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.plus.Plus;

public class homeactivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    private static final int REQUEST_CODE_LOGIN = 10;
    ImageView userdetails;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        userdetails = findViewById(R.id.textView);

        userdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homeactivity.this, MainActivity2.class));
            }
        });

//        if(isUserValidated && isPasswordValidated)  {
//            Intent intent = new Intent(homeactivity.this,MainActivity2.class);
//            intent.putExtra("GrandTotal", total);
////            intent.putExtra("login",username.getText().toString());
//            startActivity(intent);
//        }
//        Intent newIntent = new Intent(homeactivity.this, MainActivity2.class);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            newIntent.putExtras(bundle);
//        }
//        startActivity(newIntent);

//        getSupportActionBar().hide();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addApi(Plus.API).build();
        userdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signin, REQUEST_CODE_LOGIN);
            }
        });
    }

    public void viewuserdetauils(View view) {
        Intent sendData = new Intent(homeactivity.this, MainActivity2.class);
        startActivity(sendData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();

            try {
                Intent sendData = new Intent(homeactivity.this, MainActivity2.class);
                String name, email, dpUrl = "";
                name = account.getDisplayName();
                email = account.getEmail();

                dpUrl = account.getPhotoUrl().toString();
                sendData.putExtra("p_name", name);
                sendData.putExtra("p_email", email);
                sendData.putExtra("p_url", dpUrl);

                startActivity(sendData);

            } catch (Exception e) {
                Toast.makeText(homeactivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(homeactivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    public void onClick(View v) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
}