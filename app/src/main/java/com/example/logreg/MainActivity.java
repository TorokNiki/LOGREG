package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button Bejelentkez,regisztracio;
    EditText felhasznalonev,jelszo;
    private dbhelper adatbazisSegito;
    CheckBox bejelenkezveMarad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        regisztracio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Bejelentkez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!felhasznalonev.getText().toString().equals("")&& !jelszo.getText().toString().equals("")) {
                    Cursor felhasznalo = adatbazisSegito.felhasznaloEllenorzes(felhasznalonev.getText().toString(), jelszo.getText().toString());

                    if (felhasznalo.getCount() == 1 && felhasznalo.moveToFirst()) {
                        Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
                        intent.putExtra("felhasznaloNev", felhasznalo.getString(0));
                        if (bejelenkezveMarad.isChecked()) {
                            SaveSharedPreference.setUserName(MainActivity.this, felhasznalo.getString(0));
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        hiba();

                    }
                }else {
                    hiba2();
                }

            }
        });
        if(SaveSharedPreference.getUserName(MainActivity.this).length() != 0)
        {
            Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
            intent.putExtra("felhasznaloNev",SaveSharedPreference.getUserName(MainActivity.this));
            startActivity(intent);
            finish();
        }
    }
    private void hiba(){
        Toast.makeText(this,"Téves felhasználónév vagy jelsó!",Toast.LENGTH_SHORT).show();
    }
    private void hiba2(){
        Toast.makeText(this,"Nem adtál meg m,inden adatot!",Toast.LENGTH_SHORT).show();
    }
    private void init(){

        adatbazisSegito = new dbhelper(MainActivity.this);
        jelszo = findViewById(R.id.eTPassword);
        felhasznalonev = findViewById(R.id.eTEmail);
        regisztracio = findViewById(R.id.buttonRegiszt);
        Bejelentkez = findViewById(R.id.buttonBejelentkez);
        bejelenkezveMarad= findViewById(R.id.checkBox);
    }
}
