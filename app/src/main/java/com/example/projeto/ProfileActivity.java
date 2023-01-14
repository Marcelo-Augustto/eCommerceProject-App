package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView tvEmail;
    TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvUsername = (TextView) findViewById(R.id.tvUsername);

        getUser();
    }

    private void getUser() {
        Call<User> call = RetrofitClient.getInstance().getMyApi().getUser(MainActivity.user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                tvEmail.setText(user.getEmail());
                tvUsername.setText(user.getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("Error", t.toString());
            }
        });
    }

    public void goToHome(View view) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void signOut(View view) {
        Intent it = new Intent(this, MainActivity.class);
        it.putExtra("user", "");
        startActivity(it);
    }
}