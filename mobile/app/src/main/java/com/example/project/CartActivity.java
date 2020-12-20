package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

   private RecyclerView recyclerViewCart;
   private Button pay, emptyCart;
   private static final String urlCheck = "http://192.168.0.114:25016/api/check/";
   private static final String urlBasket = "http://192.168.0.114:25016/api/basket/";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_cart);

      recyclerViewCart = findViewById(R.id.cart);
      recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
      recyclerViewCart.setHasFixedSize(true);

      displayCartItems();

      pay = findViewById(R.id.pay);
      emptyCart = findViewById(R.id.cancel);

      pay.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (Basket.products.size() > 0) {
               createCheck();
               Basket.totalAmount = 0;
               startActivity(new Intent(CartActivity.this, MenuActivity.class));
            } else {
               Toast.makeText(CartActivity.this, "Корзина пуста!", Toast.LENGTH_LONG).show();
            }
         }
      });


      emptyCart.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (Basket.products.size() > 0) {
               showEmptyCarWindow();
            } else {
               Toast.makeText(CartActivity.this, "Корзина пуста!", Toast.LENGTH_LONG).show();
            }
         }
      });
   }

   private void createCheck() {
      JSONObject js = new JSONObject();
      try {
         js.put("canteen", "Столова №1");
         js.put("sum", Basket.totalAmount);
         js.put("user_id", User.id);
      } catch (JSONException e) {
         e.printStackTrace();
      }

      JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, urlCheck, js,
         new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try {
                  Basket.checkId = Long.parseLong(response.getString("check_id"));
                  getProduct(response.getString("check_id"));
               } catch (JSONException e) {
                  e.printStackTrace();
                  Toast.makeText(CartActivity.this, "Fail" + e.toString(), Toast.LENGTH_LONG).show();
               }
            }
         }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Toast.makeText(CartActivity.this, "Fail" + error.toString(), Toast.LENGTH_LONG).show();
         }
      }) {

         @Override
         public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "Bearer " + User.token);
            headers.put("Accept", "application/json; charset=UTF-8");
            headers.put("Content-Type", "application/json; charset=UTF-8");
            return headers;
         }
      };

      RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
      queue.add(jsonObjReq);
   }

   private void getProduct(String checkID) {
      ArrayList<String> product = new ArrayList<>();
      int size = 0;

      for (int j = 1; Basket.iterator < Basket.products.size(); Basket.iterator++, j++) {
         product.add(size, Basket.products.get(Basket.iterator));
         size++;

         if (j % 3 == 0) {
            createBasket(product);
            product = new ArrayList<>();
            size = 0;
         }
      }

      Basket.products = new ArrayList<>();
      Basket.iterator = 0;
   }

   private void createBasket(ArrayList<String> products) {
      JSONObject js = new JSONObject();

      try {
         js.put("user", User.id);
         js.put("check_id", Basket.checkId);
         js.put("productName", products.get(0));
         js.put("price", products.get(1));
         js.put("count", products.get(2));
      } catch (JSONException e) {
         e.printStackTrace();
      }

      JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, urlBasket, js,
         new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
         }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Toast.makeText(CartActivity.this, "Fail" + error.toString(), Toast.LENGTH_LONG).show();
         }
      }) {

         @Override
         public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "Bearer " + User.token);
            headers.put("Accept", "application/json; charset=UTF-8");
            headers.put("Content-Type", "application/json; charset=UTF-8");
            return headers;
         }
      };

      RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
      queue.add(jsonObjReq);
   }

   private void showEmptyCarWindow() {
      AlertDialog.Builder dialog = new AlertDialog.Builder(this);
      dialog.setTitle("Empty cart");
      dialog.setMessage("Желаете очистить корзину покупок?");

      LayoutInflater inflater = LayoutInflater.from(this);
      View checkout_window = inflater.inflate(R.layout.checkout_window, null);
      dialog.setView(checkout_window);

      dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int which) {
            dialogInterface.dismiss();
         }
      });

      dialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int which) {
            Basket.products = new ArrayList<>();
            Basket.totalAmount = 0;
            startActivity(new Intent(CartActivity.this, MenuActivity.class));
            finish();
         }
      });
      dialog.show();
   }

   private void displayCartItems() {
      if (Basket.products.size() > 0) {
         CartAdapter cartAdapter = new CartAdapter(this, Basket.products);
         recyclerViewCart.setAdapter(cartAdapter);
      }
   }
}
