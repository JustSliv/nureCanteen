package com.example.project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{
   Context context;
   FragmentActivity activity;
   int iterator = 0;
   ArrayList<String> products = new ArrayList<>();

   public CartAdapter(FragmentActivity activity, ArrayList<String> products) {
      this.activity = activity;
      this.products = products;
   }

   @NonNull
   @Override
   public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(activity).inflate(R.layout.cart_item_layout, parent, false);
      return new CartViewHolder(itemView);
   }

   @Override
   public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
      holder.txtProductName.setText(products.get(position + iterator));
      iterator++;
      holder.txtProductPrice.setText(products.get(position + iterator));
      iterator++;
      holder.txtProductQuantity.setText(products.get(position + iterator));
   }

   @Override
   public int getItemCount() {
      return products.size() / 3;
   }

   static class  CartViewHolder extends RecyclerView.ViewHolder {

      TextView txtProductName, txtProductPrice, txtProductQuantity;

      public CartViewHolder(@NonNull View itemView) {
         super(itemView);

         txtProductName = itemView.findViewById(R.id.textView1);
         txtProductPrice = itemView.findViewById(R.id.textView2);
         txtProductQuantity = itemView.findViewById(R.id.textView);
      }
   }
}
