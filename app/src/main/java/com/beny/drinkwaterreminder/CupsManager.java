package com.beny.drinkwaterreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class CupsManager extends AppCompatActivity implements View.OnClickListener {
    Button cup1, cup2, cup3, cup4, cup5, cup6, custom;
    TextView currentSize;
    String size;
    EditText customSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cups_manager);
        cup1 = findViewById(R.id.cup100ml);
        cup2 = findViewById(R.id.cup150ml);
        cup3 = findViewById(R.id.cup200ml);
        cup4 = findViewById(R.id.cup250ml);
        cup5 = findViewById(R.id.cup300ml);
        cup6 = findViewById(R.id.cup400ml);
        custom = findViewById(R.id.cupCustom);
        cup1.setOnClickListener(this);
        cup2.setOnClickListener(this);
        cup3.setOnClickListener(this);
        cup4.setOnClickListener(this);
        cup5.setOnClickListener(this);
        cup6.setOnClickListener(this);
        custom.setOnClickListener(this);

        currentSize = findViewById(R.id.CupSizeText);
        SharedPreferences sh = getSharedPreferences("cupsize", Context.MODE_PRIVATE);
        String temp = sh.getString("cupsize", "");
        currentSize.setText(temp);
        customSize = findViewById(R.id.customizeEditText);


    }

    @Override
    public void onClick(View v) {

        SharedPreferences sh = getSharedPreferences("cupsize", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sh.edit();

        Cups newCup = new Cups();
        Button b = (Button) v;
        switch (b.getId()) {
            case R.id.cup100ml: {
                // Do something
                newCup.setCupSize(100);
                currentSize.setText("100");
                editor.putString("cupsize", "100");
                editor.commit();
                Toast.makeText(this, "current size of your cup is : 100", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cup150ml: {    // Do something
                newCup.setCupSize(150);
                currentSize.setText("150");
                editor.putString("cupsize", "150");
                editor.commit();
                Toast.makeText(this, "current size of your cup is : 150", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cup200ml: {
                newCup.setCupSize(200);
                currentSize.setText("200");
                editor.putString("cupsize", "200");
                editor.commit();
                Toast.makeText(this, "current size of your cup is : 200", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cup250ml: {
                newCup.setCupSize(250);
                currentSize.setText("250");
                editor.putString("cupsize", "250");
                editor.commit();
                Toast.makeText(this, "current size of your cup is : 250", Toast.LENGTH_SHORT).show();
                break;

            }
            case R.id.cup300ml: {
                newCup.setCupSize(300);
                currentSize.setText("300");
                editor.putString("cupsize", "300");
                editor.commit();
                Toast.makeText(this, "current size of your cup is : 300", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.cup400ml: {

                newCup.setCupSize(400);
                currentSize.setText("400");
                editor.putString("cupsize", "400");
                editor.commit();
                Toast.makeText(this, "current size of your cup is : 400", Toast.LENGTH_SHORT).show();
                break;

            }
            case R.id.cupCustom: {

                size = customSize.getText().toString();
                boolean digitsOnly = TextUtils.isDigitsOnly(customSize.getText());
                if (customSize.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "please Enter positive numbers", Toast.LENGTH_SHORT).show();
                    customSize.setError("This field is empty!");
                }
               else if (digitsOnly) {
                    currentSize.setText(size);

                    editor.putString("cupsize", size);
                    editor.commit();
                    Toast.makeText(this, "current size of your cup is : " + size, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "please Enter positive numbers", Toast.LENGTH_SHORT).show();
                }
                break;


            }
        }

    }
}