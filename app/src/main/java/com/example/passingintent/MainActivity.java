package com.example.passingintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE_LOGIN = 10;
    private Button btnLogin;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleApiClient googleApiClient;
    TextView textBox;
    private Button mSignInButton, PhoneLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.signin_button);
        getSupportActionBar().hide();
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addApi(Plus.API).build();

//        btnLogin = (Button) findViewById(R.id.signin_button);
//        btnLogin = (Button) findViewById(R.id.buttonGetOtp);
        PhoneLoginButton =(Button) findViewById(R.id.buttonGetOtp);

//        Intent intent = this.getIntent();
//        Bundle bundle = intent.getExtras();
//        String weaponName = bundle.getString("weaponTitle");
//        Button textView = (TextView) findViewById(R.id.textView);
//        btnLogin.setText(weaponName);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signin, REQUEST_CODE_LOGIN);
            }
        });

//        Intent intent = getIntent();
//        String strTemp = intent.getStringExtra("tempstring");
//
//        btnLogin.setText(strTemp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();

            try {
                Intent sendData = new Intent(MainActivity.this, homeactivity.class);
                String name, email, gender, dpUrl = "";
                name = account.getDisplayName();
                email = account.getEmail();

                dpUrl = account.getPhotoUrl().toString();
                sendData.putExtra("p_name", name);
                sendData.putExtra("p_email", email);
                sendData.putExtra("p_url", dpUrl);

                startActivity(sendData);

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void phnelogin(View view) {
        Intent myIntent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View v) {

    }

}
