package com.sidv.quanlythuvien.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.sidv.quanlythuvien.R;

public class LogInActivity extends AppCompatActivity {
    private Button btnLogIn;
    private Button btnCancel;

    private EditText edtUserName;
    private EditText edtPassword;

    private CheckBox chkRemember;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        map();

        checkRemember();

    }

    private void checkRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("checkRemember", false);
        if (isRemember) {
            String user = sharedPreferences.getString("user", "");
            String pass = sharedPreferences.getString("pass", "");
            edtUserName.setText(user);
            edtPassword.setText(pass);
            chkRemember.setChecked(isRemember);
        }
    }

    private void map() {
        btnLogIn = findViewById(R.id.btnSignIn);
        btnCancel = findViewById(R.id.btnCancel);
        edtUserName = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        chkRemember = findViewById(R.id.chkRemember);
    }
}