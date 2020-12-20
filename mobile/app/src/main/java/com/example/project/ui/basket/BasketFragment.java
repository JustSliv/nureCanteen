package com.example.project.ui.basket;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.CartActivity;
import com.example.project.CartAdapter;
import com.example.project.MainActivity;
import com.example.project.R;

import java.util.ArrayList;


public class BasketFragment extends Fragment {

    private BasketViewModel  basketViewModel;
   private TextView txtTotalAmount;
   private RecyclerView recyclerViewCart;
   private Button nextProcess;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        basketViewModel =
                new ViewModelProvider(this).get(BasketViewModel.class);
        View root = inflater.inflate(R.layout.fragment_basket, container, false);

//       recyclerViewCart = root.findViewById(R.id.cart);
//       recyclerViewCart.setLayoutManager(new LinearLayoutManager(getActivity()));
//       recyclerViewCart.setHasFixedSize(true);
//
//       addList();

         startActivity(new Intent(getActivity(), CartActivity.class));


        return root;
    }

   private void addList(){
      ArrayList<String> products = new ArrayList<>();

      products.add("Хот-дог французький класичний");
      products.add("100 грн.");
      products.add("3x");
      products.add("Бургер");
      products.add("75 грн.");
      products.add("1x");

      displayCartItems(products);
   }

//   private void displayCartItems(ArrayList<String> products) {
//      CartAdapter cartAdapter = new CartAdapter(this, products);
//      recyclerViewCart.setAdapter(cartAdapter);
//   }

   private void displayCartItems(ArrayList<String> products) {
      CartAdapter cartAdapter = new CartAdapter(getActivity(), products);
      recyclerViewCart.setAdapter(cartAdapter);
   }
}
