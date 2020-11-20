package com.example.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.ui.basket.BasketFragment;
import com.example.project.ui.home.HomeFragment;
import com.example.project.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Salads extends AppCompatActivity {
    public FloatingActionButton plus, minus, plus1, minus1, plus2, minus2;
    public TextView textCount, textCount1, textCount2, checkout, textSum, textSum1, textSum2;
    private int count, count1, count2, checkoutCount, sumCount, sum, sum1, sum2;
    public String str, checkoutString;
    public Button btn;
    RelativeLayout root;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salads);
        root = findViewById(R.id.relativeLayoutCheckout);

        plus = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        minus = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        plus1 = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        minus1 = (FloatingActionButton) findViewById(R.id.floatingActionButton4);
        plus2 = (FloatingActionButton) findViewById(R.id.floatingActionButton5);
        minus2 = (FloatingActionButton) findViewById(R.id.floatingActionButton6);

        textCount = (TextView) findViewById(R.id.count);
        textCount1 = (TextView) findViewById(R.id.count1);
        textCount2 = (TextView) findViewById(R.id.count2);
        checkout = (TextView) findViewById(R.id.checkout);
        textSum = (TextView) findViewById(R.id.textView3);
        textSum1 = (TextView) findViewById(R.id.textView6);
        textSum2 = (TextView) findViewById(R.id.textView8);

        btn = (Button) findViewById(R.id.checkout);

        String convert = textSum.getText().toString();
        StringBuffer strBuffer = new StringBuffer(convert);
        strBuffer.delete(2, 6);
        sum = Integer.parseInt(strBuffer.toString());

        convert = textSum1.getText().toString();
        strBuffer = new StringBuffer(convert);
        strBuffer.delete(2, 6);
        sum1 = Integer.parseInt(strBuffer.toString());

        convert = textSum2.getText().toString();
        strBuffer = new StringBuffer(convert);
        strBuffer.delete(2, 6);
        sum2 = Integer.parseInt(strBuffer.toString());

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                sumCount = sumCount + sum * (count - (count - 1));
                checkoutCount++;
                checkoutString = "Заказ " + checkoutCount + " за " + sumCount + " грн.";
                btn.setText(checkoutString);

                str = count + "x";
                textCount.setText(str);
                Check(minus, textCount, count, checkoutCount);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                sumCount = sumCount - sum * (count - (count - 1));
                checkoutCount--;
                checkoutString = "Заказ " + checkoutCount + " за " + sumCount + " грн.";
                btn.setText(checkoutString);

                str = count + "x";
                textCount.setText(str);
                Check(minus, textCount, count, checkoutCount);
            }
        });

        plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1++;
                sumCount = sumCount + sum1 * (count1 - (count1 - 1));
                checkoutCount++;
                checkoutString = "Заказ " + checkoutCount + " за " + sumCount + " грн.";
                btn.setText(checkoutString);

                str = count1 + "x";
                textCount1.setText(str);
                Check(minus1, textCount1, count1, checkoutCount);
            }
        });

        minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1--;
                sumCount = sumCount - sum1 * (count1 - (count1 - 1));
                checkoutCount--;
                checkoutString = "Заказ " + checkoutCount + " за " + sumCount + " грн.";
                btn.setText(checkoutString);

                str = count1 + "x";
                textCount1.setText(str);
                Check(minus1, textCount1, count1, checkoutCount);
            }
        });

        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2++;
                sumCount = sumCount + sum2 * (count2 - (count2 - 1));
                checkoutCount++;
                checkoutString = "Заказ " + checkoutCount + " за " + sumCount + " грн.";
                btn.setText(checkoutString);

                str = count2 + "x";
                textCount2.setText(str);
                Check(minus2, textCount2, count2, checkoutCount);
            }
        });

        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count2--;
                sumCount = sumCount - sum2 * (count2 - (count2 - 1));
                checkoutCount--;
                checkoutString = "Заказ " + checkoutCount + " за " + sumCount + " грн.";
                btn.setText(checkoutString);

                str = count2 + "x";
                textCount2.setText(str);
                Check(minus2, textCount2, count2, checkoutCount);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCheckoutWindow();
            }
        });
    }

//    private void showCheckoutWindow(){
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("Checkout");
//        dialog.setMessage("Желаете перейти в");
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View checkout_window = inflater.inflate(R.layout.checkout_window, null);
//        dialog.setView(checkout_window);
//
//        dialog.setNeutralButton("Отменить", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                dialogInterface.dismiss();
//            }
//        });
//
////        dialog.setNegativeButton("Меню", new DialogInterface.OnClickListener() {
////            @Override
////            public void onClick(DialogInterface dialogInterface, int which) {
////                FragmentManager fm = getSupportFragmentManager();
////                HomeFragment homeFragment = new HomeFragment();
////                fm.beginTransaction().replace(R.id.relativeLayoutSalads, homeFragment).commit();
////                finish();
////            }
////        });
//
//       dialog.setPositiveButton("Корзина", new DialogInterface.OnClickListener() {
//           @Override
//           public void onClick(DialogInterface dialogInterface, int which) {
//               SlideshowFragment slideshowFragment = new SlideshowFragment();
//               FragmentManager fm = getSupportFragmentManager();
//               FragmentTransaction ft = fm.beginTransaction();
//               ft.replace(R.id.relativeLayoutSalads, slideshowFragment);
//               ft.commit();
//               finish();
//           }
//       });
//        dialog.show();
//    }

    private void showCheckoutWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Checkout");
        dialog.setMessage("Желаете оформить заказ?");

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
                startActivity(new Intent(Salads.this, MenuActivity.class));
                finish();
            }
        });


//        dialog.setNegativeButton("Меню", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                FragmentManager fm = getSupportFragmentManager();
//                HomeFragment homeFragment = new HomeFragment();
//                fm.beginTransaction().replace(R.id.relativeLayoutSalads, homeFragment).commit();
//                finish();
//            }
//        });

//       dialog.setPositiveButton("Корзина", new DialogInterface.OnClickListener() {
//           @Override
//           public void onClick(DialogInterface dialogInterface, int which) {
//               SlideshowFragment slideshowFragment = new SlideshowFragment();
//               FragmentManager fm = getSupportFragmentManager();
//               FragmentTransaction ft = fm.beginTransaction();
//               ft.replace(R.id.relativeLayoutSalads, slideshowFragment);
//               ft.commit();
//               finish();
//           }
//       });
        dialog.show();
    }


    public void Check(FloatingActionButton minus, TextView textCount, int count, int checkoutCount) {
        if (count < 1) {
            minus.setVisibility(View.INVISIBLE);
            textCount.setVisibility(View.INVISIBLE);
        }
        if (count > 0) {
            minus.setVisibility(View.VISIBLE);
            textCount.setVisibility(View.VISIBLE);
        }
        if (checkoutCount < 1) {
            checkout.setVisibility(View.INVISIBLE);
        }
        if (checkoutCount > 0) {
            checkout.setVisibility(View.VISIBLE);
        }
    }
}