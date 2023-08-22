package com.echcherqaoui.khalid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText username, password;
    private Button login;
    private final String uname = "test";
    private final String pass = "1212";
    private SharedPreferences sharedPreferences;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            checkFields();
        }
    };

    void checkFields(){
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        login.setEnabled(!username.equals("") && !password.equals(""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        login.setBackgroundColor(Color.BLACK);

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        sharedPreferences = getSharedPreferences("loginPref", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username.getText().toString());
        editor.putString("pwdKey", password.getText().toString());
        editor.apply();

        checkFields();

        login.setOnClickListener(view -> {
            // Server simulation
            if (username.getText().toString().equals(uname) && password.getText().toString().equals(pass)){
                Intent intent = new Intent(MainActivity.this, finderActivity.class);

                startActivity(intent);
            }else {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

                dlgAlert.setMessage("Username or password is incorrect");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
    }
}