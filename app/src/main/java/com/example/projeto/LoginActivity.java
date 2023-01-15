package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

    }

    public void goToHome(View view) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void signIn(View view) {
        user = new HashMap<String, String>();
        user.put("email", etEmail.getText().toString());
        user.put("password", etPassword.getText().toString());

        Call<Response> call = RetrofitClient.getInstance().getMyApi().login(user);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response resp = response.body();

                if (resp.isLogged() instanceof String) {
                    goToHome();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.v("Error", t.toString());
            }
        });
    }

    public void goToHome() {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("user", etEmail.getText().toString());
        startActivity(it);
    }

    public void goToSignUp(View view) {
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }
}