package com.example.projeto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCartActivity extends AppCompatActivity {
    ArrayList<CartProduct> products;
    ShopcartAdapter adaptador;
    float totalPrice = 0.00F;
    TextView tvTotalPrice;
    private static ShoppingCartActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_compras);
        products = new ArrayList<CartProduct>();

        instance = this;

        tvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);

        RecyclerView rvProducts = findViewById(R.id.cartRecycler);
        adaptador = new ShopcartAdapter(products);
        RecyclerView.LayoutManager layout =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvProducts.setLayoutManager(layout);
        rvProducts.setAdapter(adaptador);

        getCartFromApi();
    }

    private void getCartFromApi() {
        for (int i = 1; i <= 20; i++) {
            Call<CartProduct> call = RetrofitClient.getInstance().getMyApi().getCart(MainActivity.user, i);
            call.enqueue(new Callback<CartProduct>() {
                @Override
                public void onResponse(@NonNull Call<CartProduct> call, @NonNull Response<CartProduct> response) {
                    CartProduct prod = response.body();
                    totalPrice += prod.getPrice();
                    products.add(prod);
                    adaptador.notifyDataSetChanged();
                    tvTotalPrice.setText("R$ " + Float.toString(totalPrice));
                }

                @Override
                public void onFailure(@NonNull Call<CartProduct> call, @NonNull Throwable t) {
                    Log.d("Error", t.toString());
                }
            });
        }
    }

    public static ShoppingCartActivity getInstance() {
        return instance;
    }

    public void goToHome(View view) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }
}