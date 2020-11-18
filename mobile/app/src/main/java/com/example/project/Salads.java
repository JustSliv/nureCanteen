package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Salads extends AppCompatActivity {

    public FloatingActionButton plus, minus, plus1, minus1, plus2, minus2;
    public TextView textCount, textCount1, textCount2, checkout, textSum, textSum1, textSum2;
    private int count, count1, count2, checkoutCount, sumCount, sum, sum1, sum2;
    public String str, checkoutString;
    public Button btn;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salads);

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