package com.example.passwordgenrator;

import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Button button;
    SwitchMaterial uppercase;
    SwitchMaterial lowercase;
    SwitchMaterial numbers;
    SwitchMaterial symbols;
    Slider slider;
    TextView result;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        button = (Button) findViewById(R.id.button);
        uppercase = (SwitchMaterial) findViewById(R.id.uppercase);
        lowercase = (SwitchMaterial) findViewById(R.id.lowercase);
        numbers = (SwitchMaterial) findViewById(R.id.numbers);
        symbols = (SwitchMaterial) findViewById(R.id.symbols);
        slider = (Slider) findViewById(R.id.seekBar);
        result = (TextView) findViewById(R.id.textView);

        final Handler handler = new Handler();
        final int delay = 100; // 100 milliseconds == 0.1 second

        handler.postDelayed(new Runnable() {
            public void run() {
                if (!lowercase.isChecked() && !numbers.isChecked() && !symbols.isChecked()) {
                    uppercase.setChecked(true);

                    uppercase.setEnabled(false);
                } else {
                    uppercase.setEnabled(true);
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                if (!lowercase.isChecked() && !numbers.isChecked() && !symbols.isChecked()) {
                    uppercase.setChecked(true);
                }
                int length = (int) value;
                String pass = generateRandomPassword(length, uppercase.isChecked(), lowercase.isChecked(), numbers.isChecked(), symbols.isChecked());
                result.setText(pass);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lowercase.isChecked() && !numbers.isChecked() && !symbols.isChecked()) {
                    uppercase.setChecked(true);
                }
                int length = (int) slider.getValue();
                String pass = generateRandomPassword(length, uppercase.isChecked(), lowercase.isChecked(), numbers.isChecked(), symbols.isChecked());
                result.setText(pass);
            }
        });
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checks the orientation of the screen
////        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
////            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
////        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
////            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
////        }
//    }


    public static String generateRandomPassword(int max_length, boolean upperCase, boolean lowerCase, boolean numbers, boolean specialCharacters) {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?/{}~|";
        String allowedChars = "";

        Random rn = new Random();
        StringBuilder sb = new StringBuilder(max_length);

        //this will fulfill the requirements of atleast one character of a type.
        if (upperCase) {
            allowedChars += upperCaseChars;
            sb.append(upperCaseChars.charAt(rn.nextInt(upperCaseChars.length() - 1)));
        }

        if (lowerCase) {
            allowedChars += lowerCaseChars;
            sb.append(lowerCaseChars.charAt(rn.nextInt(lowerCaseChars.length() - 1)));
        }

        if (numbers) {
            allowedChars += numberChars;
            sb.append(numberChars.charAt(rn.nextInt(numberChars.length() - 1)));
        }

        if (specialCharacters) {
            allowedChars += specialChars;
            sb.append(specialChars.charAt(rn.nextInt(specialChars.length() - 1)));
        }


        //fill the allowed length from different chars now.
        for (int i = sb.length(); i < max_length; ++i) {
            sb.append(allowedChars.charAt(rn.nextInt(allowedChars.length())));
        }

        return sb.toString();
    }
}