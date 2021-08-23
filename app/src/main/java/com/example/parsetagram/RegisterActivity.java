package com.example.parsetagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {
    public static final String TAG = "RegisterActivity";

    private EditText etSetUsername;
    private EditText etSetPassword;
    private Button btnRegisterSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etSetUsername = findViewById(R.id.etSetUsername);
        etSetPassword = findViewById(R.id.etSetPassword);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etSetUsername.getText().toString();
                String pass = etSetPassword.getText().toString();
                register(username, pass);
            }
        });
    }

    private void register(String usern, String pass) {
        ParseUser user = new ParseUser();

        user.setUsername(usern);
        user.setPassword(pass);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                    return;
                }
                goMainActivity();
            }
        });

    }
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
