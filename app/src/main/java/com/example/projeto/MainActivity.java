package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<Product> products;
    AdapterMain adaptador;
    public static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        products = new ArrayList<Product>();

        Intent it = getIntent();
        user = it.getStringExtra("user");

        RecyclerView rvProductsMain = findViewById(R.id.mainListRecycler);
        adaptador = new AdapterMain(products);
        RecyclerView.LayoutManager layout =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProductsMain.setLayoutManager(layout);
        rvProductsMain.setAdapter(adaptador);

        getProductsFromApi();

    }

    private void getProductsFromApi() {
        for (int i = 1; i <= 7; i++) {
            Call<Product> call = RetrofitClient.getInstance().getMyApi().getProduct(i);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    Product prod = response.body();
                    products.add(prod);
                    adaptador.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    Log.d("TESTE", t.toString());
                }
            });
        }

    }

    public void LaunchShopCart(View view) {
        Intent it = new Intent(this, ShoppingCartActivity.class);
        startActivity(it);
    }

    public void goToProfile(View view) {
        Intent it;
        if (user instanceof String && !user.isEmpty()) {
            it = new Intent(this, ProfileActivity.class);
        } else {
            it = new Intent(this, LoginActivity.class);
        }
        startActivity(it);
    }
}
