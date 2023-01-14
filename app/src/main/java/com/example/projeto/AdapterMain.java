package com.example.projeto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {
    ArrayList<Product> products;
    Context context;
    HashMap<String, String> req;

    public AdapterMain(ArrayList<Product> products) {
        this.products = products;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvPrice;
        final ImageView ivPhoto;
        final Button btnBuy;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
            btnBuy = (Button) view.findViewById(R.id.btnBuy);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listmain, parent, false);

        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("" + product.getPrice());

        String urlImage = product.getImgUrl();
        Picasso.get().load(urlImage).into(holder.ivPhoto);

        req = new HashMap<String, String>();

        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req.put("product_name", products.get(position).getName());
                req.put("price", products.get(position).getPrice().toString());
                req.put("img", products.get(position).getImgUrl());
                req.put("cartUser", MainActivity.user);
                Log.v("Hash ", req.toString());

                Call<Response> call = RetrofitClient.getInstance().getMyApi().addToCart(req);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Response resp = response.body();
                        Log.v("Status ", resp.getStatus());
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.v("Status ", t.toString());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}

