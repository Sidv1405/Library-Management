package com.sidv.quanlythuvien.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sidv.quanlythuvien.DAO.ThuThuDAO;
import com.sidv.quanlythuvien.R;

public class LogInActivity extends AppCompatActivity {
    private Button btnLogIn;
    private Button btnCancel;

    private EditText edtUserName;
    private EditText edtPassword;

    private CheckBox chkRemember;

    private ThuThuDAO thuThuDAO;

    private boolean isLoggedIn = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        map();

        checkRemember();

        tapLogIn();

        tapCancel();

    }

    private void tapCancel() {
        btnCancel.setOnClickListener(v -> {
            edtUserName.setText("");
            edtPassword.setText("");
        });
    }

    private void tapLogIn() {
        thuThuDAO = new ThuThuDAO(this);
        btnLogIn.setOnClickListener(v -> {
            String username = edtUserName.getText().toString();
            String password = edtUserName.getText().toString();
            boolean check = thuThuDAO.checkLogIn(username, password);

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter account and password", Toast.LENGTH_SHORT).show();
            } else {
                if (Boolean.TRUE.equals(check)) {
                    Toast.makeText(this, "Login success!", Toast.LENGTH_SHORT).show();
                    if (chkRemember.isChecked()) {
                        remember(username, password);
                    }
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    isLoggedIn = true;
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Login fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void remember(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checkRemember", true);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void checkRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("checkRemember", false);
        if (isRemember) {
            String user = sharedPreferences.getString("username", "");
            String pass = sharedPreferences.getString("password", "");
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