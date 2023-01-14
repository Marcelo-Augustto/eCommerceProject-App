package com.example.projeto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {
    String BASE_URL = "http://10.0.2.2:80/eCommerceProject/";

    @GET("get-product-by-id/{prodId}")
    Call<Product> getProduct(@Path("prodId") int prodId);

    @GET("get-cart-product-by-user/{user}/{prodId}")
    Call<CartProduct> getCart(@Path("user") String user, @Path("prodId") int prodId);

    @FormUrlEncoded
    @POST("add-to-cart")
    Call<Response> addToCart(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("change-cart")
    Call<Response> removeProduct(@FieldMap HashMap<String, String> data);
}
