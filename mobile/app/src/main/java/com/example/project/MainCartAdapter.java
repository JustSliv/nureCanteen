package com.example.project;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainCartAdapter extends RecyclerView.Adapter<MainCartAdapter.CartViewHolder> {
   Context context;
   int iterator = 0;

   public MainCartAdapter(Context context) {
      this.context = context;
   }

   @NonNull
   @Override
   public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(context).inflate(R.layout.check_cart, parent, false);
      return new CartViewHolder(itemView);
   }

   @Override
   public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
      holder.productName.setText(Check.basket.get(position + iterator));
      iterator++;
      holder.productCount.setText(Check.basket.get(position + iterator));
      iterator++;
      holder.productPrice.setText(Check.basket.get(position + iterator));
//      iterator++;
//      holder.checkID.setText(Check.basket.get(position + iterator));
//      iterator++;
//      holder.canteen.setText(Check.basket.get(position + iterator));
//
//      iterator++;
//      StringBuilder stringBuilder = new StringBuilder();
//      stringBuilder.append("Дата и время оплаты: ").append(Check.basket.get(position + iterator)).append(" ");
//      iterator++;
//      stringBuilder.append(Check.basket.get(position + iterator)); // Data + time
//
//      holder.data.setText(stringBuilder.toString());
//      iterator++;
//      holder.cardSum.setText(Check.basket.get(position + iterator));
   }

   @Override
   public int getItemCount() {
      return Check.basket.size() / 3;
   }

   static class CartViewHolder extends RecyclerView.ViewHolder {
      TextView checkID, canteen, productName, productCount, productPrice, cardSum, data;

      public CartViewHolder(@NonNull View itemView) {
         super(itemView);
         checkID = itemView.findViewById(R.id.checkNumberID);
         canteen = itemView.findViewById(R.id.canteen);
         cardSum = itemView.findViewById(R.id.cardSum);
         data = itemView.findViewById(R.id.data);

         productName = itemView.findViewById(R.id.productName);
         productCount = itemView.findViewById(R.id.productCount);
         productPrice = itemView.findViewById(R.id.productPrice);
      }
   }
}
