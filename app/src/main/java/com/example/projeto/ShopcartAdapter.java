package com.example.projeto;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class ShopcartAdapter extends RecyclerView.Adapter<ShopcartAdapter.ViewHolder> {
    ArrayList<CartProduct> products;
    HashMap<String, String> req;

    public ShopcartAdapter(ArrayList<CartProduct> products) {
        this.products = products;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final TextView tvQuantity;
        final TextView tvPrice;
        final CardView CardPhoto;
        final ImageView ivPhoto;
        final Button btnEdit;
        final Button btnRemove;



        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.itemNameCart);
            tvQuantity = (TextView) view.findViewById(R.id.txtQuantityShopCart);
            tvPrice = (TextView) view.findViewById(R.id.txtValueItemCart);
            CardPhoto = (CardView) view.findViewById(R.id.cardFoto);
            ivPhoto = (ImageView) view.findViewById(R.id.ivPhotoShop);
            btnEdit = (Button) view.findViewById(R.id.btnEditCart);
            btnRemove = (Button) view.findViewById(R.id.btnRemove);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartProduct product = products.get(position);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("R$ " + product.getPrice());
        holder.tvQuantity.setText("" + product.getQuantity());

        String urlImage = product.getImgUrl();
        Picasso.get().load(urlImage).into(holder.ivPhoto);

        req = new HashMap<String, String>();

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                req.put("productName", products.get(position).getName());
                req.put("action", "delete");
                req.put("user", MainActivity.user);
                Log.v("Status", req.toString());
                Call<Response> call = RetrofitClient.getInstance().getMyApi().removeProduct(req);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Response resp = response.body();
                        Log.v("Status", resp.getStatus());
                        ShoppingCartActivity.getInstance().finish();
                        ShoppingCartActivity.getInstance().startActivity(ShoppingCartActivity.getInstance().getIntent());
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.v("Error", t.toString());
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
