package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckActivity extends AppCompatActivity {
   private RecyclerView recyclerViewCart;
   Spinner spinner;
   ArrayList<String> checks;
   TextView checkID, canteen, cardSum, data;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_check);

      spinner = findViewById(R.id.spinner);

      recyclerViewCart = findViewById(R.id.checkLayout);
      recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
      recyclerViewCart.setHasFixedSize(true);

      checkID = findViewById(R.id.checkNumberID);
      canteen = findViewById(R.id.canteen);
      cardSum = findViewById(R.id.cardSum);
      data = findViewById(R.id.data);

      getCheckID();

   }

   private void setSpinner(int checkID){
      checks.add(String.valueOf(checkID));
      ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, checks);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner.setAdapter(adapter);

      AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String item = (String) parent.getItemAtPosition(position);
            Check.check = new ArrayList<>();
            Check.basket = new ArrayList<>();
            getCheck(Integer.parseInt(item));
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
      };
      spinner.setOnItemSelectedListener(itemSelectedListener);
   }
   private void getCheckID() {
      String urlGetCheckID = "http://192.168.0.114:25016/api/check/getCheckID/" + User.id;

      JsonArrayRequest json = new JsonArrayRequest(
         Request.Method.GET, urlGetCheckID, null,
         new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               try {
                  JSONArray jsonarray = new JSONArray(response.toString());

                  checks = new ArrayList<>();
                   for (int i = 0; i < jsonarray.length(); i++) {
//                      getCheck(jsonarray.getInt(i));
                     setSpinner(jsonarray.getInt(i));
                   }

//                  getCheck(jsonarray.getInt(jsonarray.length() - 1));
               } catch (JSONException e) {
                  e.printStackTrace();
               }
            }
         },

         new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
               Toast.makeText(CheckActivity.this, "Authorization Error(" + error.toString(), Toast.LENGTH_LONG).show();
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


      RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
      rq.add(json);
   }

   private void getCheck(int checkID) {
      String urlGetCheck = "http://192.168.0.114:25016/api/check/getCheck/" + checkID;

      JsonArrayRequest json = new JsonArrayRequest(
         Request.Method.GET, urlGetCheck, null,
         new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               try {
                  JSONArray jsonarray = new JSONArray(response.toString());
                  JSONObject jsonobject = jsonarray.getJSONObject(0);

                  String canteen = jsonobject.getString("canteen");
                  String sum = jsonobject.getString("sum");
                  String date = jsonobject.getString("purchaseDate");
                  String time = jsonobject.getString("time");
                  getBasketID(checkID, canteen, date, time, sum);
               } catch (JSONException e) {
                  e.printStackTrace();
               }
            }
         },

         new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
               Toast.makeText(CheckActivity.this, "Authorization Error(" + error.toString(), Toast.LENGTH_LONG).show();
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

      RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
      rq.add(json);
   }

   private void getBasketID(int checkID, String canteen, String date, String time, String cardSum) {
      String urlGetBasketID = "http://192.168.0.114:25016/api/basket/getBasketID/" + checkID;

      JsonArrayRequest json = new JsonArrayRequest(
         Request.Method.GET, urlGetBasketID, null,
         new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               try {
                  JSONArray jsonarray = new JSONArray(response.toString());
                  for (int i = 0; i < jsonarray.length(); i++) {
                     getBasket(jsonarray.getInt(i), canteen, checkID, date, time, cardSum);
                  }
               } catch (JSONException e) {
                  e.printStackTrace();
               }
            }
         },

         new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
               Toast.makeText(CheckActivity.this, "Authorization Error(" + error.toString(), Toast.LENGTH_LONG).show();
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


      RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
      rq.add(json);
   }

   private void getBasket(int basketID, String canteen, int checkID, String date, String time, String cardSum) {
      String urlGetBasket = "http://192.168.0.114:25016/api/basket/getBasket/" + basketID;

      JsonArrayRequest json = new JsonArrayRequest(
         Request.Method.GET, urlGetBasket, null,
         new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
               try {
                  JSONArray jsonarray = new JSONArray(response.toString());
                  JSONObject jsonobject = jsonarray.getJSONObject(0);


                  String productName = jsonobject.getString("productName");
                  Check.basket.add(productName);
                  String count = jsonobject.getString("count") + "x";
                  Check.basket.add(count);
                  String price = jsonobject.getString("price");
                  Check.basket.add(price);

//                  Check.basket.add("Чек № " + checkID);
//                  Check.basket.add(canteen);
//                  Check.basket.add(date);
//                  Check.basket.add(time);
//                  Check.basket.add("Общая сумма: " + cardSum);



                  displayCartItems(checkID, canteen, date, time, cardSum);
               } catch (JSONException e) {
                  e.printStackTrace();
               }
            }
         },

         new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
               Toast.makeText(CheckActivity.this, "Authorization Error(" + error.toString(), Toast.LENGTH_LONG).show();
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

      RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
      rq.add(json);
   }

   @SuppressLint("SetTextI18n")
   private void displayCartItems(int checkIDTxt, String canteenTxt, String dateTxt, String timeTxt, String cardSumTxt) {
      checkID.setText("Чек № "+ checkIDTxt);
      canteen.setText(canteenTxt);
      data.setText("Дата и время оплаты: " + dateTxt + " " + timeTxt);
      cardSum.setText("Общая сумма: " + cardSumTxt);

      MainCartAdapter mainCartAdapter = new MainCartAdapter(this);
      recyclerViewCart.setAdapter(mainCartAdapter);
   }
}
