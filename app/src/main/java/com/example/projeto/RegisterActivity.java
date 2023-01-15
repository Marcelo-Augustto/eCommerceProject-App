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

public class RegisterActivity extends AppCompatActivity {
    EditText etEmail;
    EditText etName;
    EditText etPassword;
    Button btnSignUp;
    HashMap<String, String> user;
//    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = (EditText) findViewById(R.id.etEmailReg);
        etName = (EditText) findViewById(R.id.etNameReg);
        etPassword = (EditText) findViewById(R.id.etPasswordReg);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

    }

    public void signUp(View view) {
        user = new HashMap<String, String>();
        user.put("name", etName.getText().toString());
        user.put("email", etEmail.getText().toString());
        user.put("password", etPassword.getText().toString());

        Call<Response> call = RetrofitClient.getInstance().getMyApi().register(user);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response resp = response.body();
                Log.v("Status", resp.getStatus());

                if (resp.getStatus().equals("success")) {
                    Toast.makeText(getApplicationContext(), "The user has been successfully registered", Toast.LENGTH_LONG).show();
                    goToSignIn(view);
                } else {
                    Toast.makeText(getApplicationContext(), "This email has already been registered", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.v("Error", t.toString());
            }
        });
    }

    public void goToSignIn(View view) {
        Intent it = new Intent(this, LoginActivity.class);
        startActivity(it);
    }
}