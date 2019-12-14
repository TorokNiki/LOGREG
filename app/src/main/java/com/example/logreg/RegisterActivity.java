package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    Button Regisztracio,Vissza;
    EditText email,felhasznalonev,jelszo,teljesnev;
    private dbhelper adatbazisSegito;
    boolean joEmail, joFelhnev,joJelszo,joTeljesnev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        Vissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Regisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!jelszo.getText().toString().equals("")&& !email.getText().toString().equals("")&& !teljesnev.getText().toString().equals("") && !felhasznalonev.getText().toString().equals("")){

                    adatRogzites();

                }
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                joEmail= !email.getText().toString().equals("");
                vizsgalat();
            }
        });
        jelszo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                joJelszo= !jelszo.getText().toString().equals("");
                vizsgalat();
            }
        });
        felhasznalonev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                joFelhnev= !felhasznalonev.getText().toString().equals("");
                vizsgalat();
            }
        });
        teljesnev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                joTeljesnev= !teljesnev.getText().toString().equals("");
                vizsgalat();
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (adatbazisSegito.ellenorzesEmail(email.getText().toString())){
                        email.setTextColor(Color.RED);
                    }else {
                        email.setTextColor(Color.GREEN);
                    }
                }


            }
        });
        felhasznalonev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (adatbazisSegito.ellenorzesFelhnev(felhasznalonev.getText().toString())){
                        felhasznalonev.setTextColor(Color.RED);
                    }else {
                        felhasznalonev.setTextColor(Color.GREEN);
                    }
                }

            }
        });

    }
    public void adatRogzites()
    {
        Boolean eredmeny=false;
        boolean helyesEmail = Pattern.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", email.getText().toString());
        boolean helyesTNev= Pattern.matches("^[A-ZÖÜÓŐÚŰÁÉÍ]{1}[a-zöüóőúéáűí]+\\s[A-ZÖÜÓŐÚŰÁÉÍ]{1}[a-zöüóőúéáűí]+",teljesnev.getText().toString());
        if (helyesEmail && helyesTNev){
        String Email = email.getText().toString();
        String Jelszo = jelszo.getText().toString();
        String TeljesNev = teljesnev.getText().toString();
        String felhnev = felhasznalonev.getText().toString();
        eredmeny = adatbazisSegito.adatRogzites(Email,felhnev,Jelszo,TeljesNev);
        }else {
            eredmeny= false;
            Toast.makeText(this, "Sikertelen adat rögzítés! Nem megfelelő e-mail vagy név formátum!", Toast.LENGTH_SHORT).show();
        }

        if (eredmeny) {
            Toast.makeText(this, "Adatrögzítés sikeres!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(this, "Adatrögzítés nem sikerült! Az e-mail vagy a felhasználónév már foglalt", Toast.LENGTH_SHORT).show();
    }
    private void init(){
        adatbazisSegito = new dbhelper(RegisterActivity.this);
        jelszo = findViewById(R.id.eTPasswordFel);
        email = findViewById(R.id.eTemailFel);
        teljesnev = findViewById(R.id.eTTeljesNev);
        felhasznalonev = findViewById(R.id.eTFelhasznaloNev);
        Regisztracio = findViewById(R.id.buttonRegisztacio);
        Regisztracio.setEnabled(false);
        Vissza = findViewById(R.id.buttonVissza);

    }

    public void vizsgalat(){
        if (joEmail&&joFelhnev&&joJelszo&&joTeljesnev){
            Regisztracio.setEnabled(true);
        }else {
            Regisztracio.setEnabled(false);
        }
    }
}
