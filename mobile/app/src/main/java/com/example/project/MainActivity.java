package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   private Button btnSignIn, btnRegister;
   private RelativeLayout root;
   private static final String URL_REGISTER = "http://192.168.0.114:25016/api/register";
   private static final String URL_AUTHENTICATE = "http://192.168.0.114:25016/api/authenticate";
   private static final String URL_USER = "http://192.168.0.114:25016/api/user";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      btnSignIn = findViewById(R.id.btnSignIn);
      btnRegister = findViewById(R.id.btnRegister);
      root = findViewById(R.id.root_element);

      btnRegister.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            showRegisterWindow();
         }
      });

      btnSignIn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            showSignInWindow();
         }
      });
   }

   private void showSignInWindow() {
      AlertDialog.Builder dialog = new AlertDialog.Builder(this);
      dialog.setTitle("Войти");
      dialog.setMessage("Введите данные для входа");

      LayoutInflater inflater = LayoutInflater.from(this);
      View sign_in_window = inflater.inflate(R.layout.sign_in_window, null);
      dialog.setView(sign_in_window);

      final MaterialEditText username = sign_in_window.findViewById(R.id.username);
      final MaterialEditText password = sign_in_window.findViewById(R.id.password);

      dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int which) {
            dialogInterface.dismiss();
         }
      });

      dialog.setPositiveButton("Авторизоваться", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int which) {
            if (TextUtils.isEmpty(username.getText().toString())) {
               Snackbar.make(root, "Введите логин", Snackbar.LENGTH_SHORT).show();
               return;
            }

            if (password.getText().toString().length() < 5) {
               Snackbar.make(root, "Введите пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show();
               return;
            }

            // Auth User
            getToken(username, password);
            User.username = username;
            User.password = password;
//            startActivity(new Intent(MainActivity.this, MenuActivity.class));
//            finish();
         }
      });

      dialog.show();
   }

   private void showRegisterWindow() {
      AlertDialog.Builder dialog = new AlertDialog.Builder(this);
      dialog.setTitle("Регистрация");
      dialog.setMessage("Заполните все поля");

      LayoutInflater inflater = LayoutInflater.from(this);
      View register_window = inflater.inflate(R.layout.register_window, null);
      dialog.setView(register_window);

      final MaterialEditText email = register_window.findViewById(R.id.emailField);
      final MaterialEditText username = register_window.findViewById(R.id.username);
      final MaterialEditText password = register_window.findViewById(R.id.password);
      final MaterialEditText phone = register_window.findViewById(R.id.phone);

      dialog.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int which) {
            dialogInterface.dismiss();
         }
      });

      dialog.setPositiveButton("Зарегистрироваться", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int which) {
            if (TextUtils.isEmpty(email.getText().toString())) {
               Snackbar.make(root, "Введите почту", Snackbar.LENGTH_SHORT).show();
               return;
            }

            if (TextUtils.isEmpty(username.getText().toString())) {
               Snackbar.make(root, "Введите логин", Snackbar.LENGTH_SHORT).show();
               return;
            }

            if (TextUtils.isEmpty(phone.getText().toString())) {
               Snackbar.make(root, "Введите номер телефона", Snackbar.LENGTH_SHORT).show();
               return;
            }

            if (password.getText().toString().length() < 5) {
               Snackbar.make(root, "Введите пароль, который более 5 символов", Snackbar.LENGTH_SHORT).show();
               return;
            }

            // Registration User
            registration(username, password, phone, email);
         }
      });
      dialog.show();
   }

   private void registration
      (MaterialEditText username, MaterialEditText password,
       MaterialEditText phone, MaterialEditText email) {
      JSONObject js = new JSONObject();
      try {
         js.put("username", username.getText().toString());
         js.put("email", email.getText().toString());
         js.put("password", password.getText().toString());
         js.put("phone", phone.getText().toString());
      } catch (JSONException e) {
         e.printStackTrace();
      }

      JsonObjectRequest jsonObjReq = new JsonObjectRequest(
         Request.Method.POST, URL_REGISTER, js,
         new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               Toast.makeText(MainActivity.this, "Register Success", Toast.LENGTH_LONG).show();
            }
         },
         new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
               Toast.makeText(MainActivity.this, "Register Error(" + error.toString(), Toast.LENGTH_LONG).show();
            }
         });

      RequestQueue requestQueue = Volley.newRequestQueue(this);
      requestQueue.add(jsonObjReq);
   }

   private void getToken(MaterialEditText username, MaterialEditText password) {
      JSONObject js = new JSONObject();

      try {
         js.put("username", username.getText().toString());
         js.put("password", password.getText().toString());
      } catch (JSONException e) {
         e.printStackTrace();
      }

      JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, URL_AUTHENTICATE, js,
         new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try {
                  String accessToken = response.getString("id_token");
                  getData(accessToken);
//                  Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                  User.token = accessToken;
                  startActivity(new Intent(MainActivity.this, MenuActivity.class));
                  finish();
                  //Toast.makeText(MainActivity.this, accessToken, Toast.LENGTH_LONG).show();
               } catch (JSONException e) {
                  e.printStackTrace();
                  Toast.makeText(MainActivity.this, "Fail" + e.toString(), Toast.LENGTH_LONG).show();
               }
            }
         }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            Toast.makeText(MainActivity.this, "Fail" + error.toString(), Toast.LENGTH_LONG).show();
         }
      }) {
      };

      RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
      queue.add(jsonObjReq);
   }

   private void getData(String token) {
      JsonObjectRequest json = new JsonObjectRequest(
         Request.Method.GET, URL_USER, null,
         new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               try {
                  JSONObject jObj = new JSONObject(response.toString());
//                  String log = jObj.getString("username");
//                  String pas = jObj.getString("password");
                  User.id = jObj.getString("id");
//                  Toast.makeText(MainActivity.this, user_id, Toast.LENGTH_LONG).show();
               } catch (JSONException e) {
                  e.printStackTrace();
                  Toast.makeText(MainActivity.this, "Authorization Error(" + e.toString(), Toast.LENGTH_LONG).show();
               }
            }
         },
         new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               error.printStackTrace();
               Toast.makeText(MainActivity.this, "Authorization Error(" + error.toString(), Toast.LENGTH_LONG).show();
            }
         }) {

         @Override
         public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization", "Bearer " + token);
            headers.put("Accept", "application/json; charset=UTF-8");
            headers.put("Content-Type", "application/json; charset=UTF-8");
            return headers;
         }
      };

      RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
      rq.add(json);
   }
}
