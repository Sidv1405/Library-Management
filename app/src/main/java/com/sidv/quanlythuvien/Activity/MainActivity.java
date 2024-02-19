package com.sidv.quanlythuvien.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.sidv.quanlythuvien.Fragment.FragmentDMK;
import com.sidv.quanlythuvien.Fragment.FragmentDT;
import com.sidv.quanlythuvien.Fragment.FragmentQLLS;
import com.sidv.quanlythuvien.Fragment.FragmentQLPM;
import com.sidv.quanlythuvien.Fragment.FragmentQLS;
import com.sidv.quanlythuvien.Fragment.FragmentQLTV;
import com.sidv.quanlythuvien.Fragment.FragmentTTV;
import com.sidv.quanlythuvien.Fragment.FragmentTop;
import com.sidv.quanlythuvien.R;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    private View headerLayout;
    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.themnd).setVisible(false);
        headerLayout = navigationView.getHeaderView(0);
        txtUser = headerLayout.findViewById(R.id.txtUser);
        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        txtUser.setText("Welcome " + user + "!");
        if (user.equalsIgnoreCase("admin")) {
            navigationView.getMenu().findItem(R.id.themnd).setVisible(true);
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        navigationView.setNavigationItemSelectedListener(item -> {
            FragmentManager manager = getSupportFragmentManager();
            Fragment fragment;
            if (item.getItemId() == R.id.qlpm) {
                fragment = new FragmentQLPM();
            } else if (item.getItemId() == R.id.qlls) {
                fragment = new FragmentQLLS();
            } else if (item.getItemId() == R.id.qls) {
                fragment = new FragmentQLS();
            } else if (item.getItemId() == R.id.qltv) {
                fragment = new FragmentQLTV();
            } else if (item.getItemId() == R.id.top10) {
                fragment = new FragmentTop();
            } else if (item.getItemId() == R.id.doanhthu) {
                fragment = new FragmentDT();
            } else if (item.getItemId() == R.id.themnd) {
                fragment = new FragmentTTV();
            } else if (item.getItemId() == R.id.doimk) {
                fragment = new FragmentDMK();
            } else if (item.getItemId() == R.id.dx) {
                fragment = new FragmentQLPM();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn muốn thoát khỏi ứng dụng ?");
                builder.setPositiveButton("Không đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this, LogInActivity.class));
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                fragment = new FragmentQLPM();
            }
            manager.beginTransaction()
                    .replace(R.id.maincontent, fragment)
                    .commit();
            drawerLayout.closeDrawer(GravityCompat.START);
            setTitle(item.getTitle());
            return false;
        });
        FragmentQLPM qLphieumuon = new FragmentQLPM();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.maincontent, qLphieumuon)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}