package com.example.navigationview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Navigation extends AppCompatActivity {

    Bundle bundle;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        bundle = this.getIntent().getExtras();

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);

        TextView txtNombre = header.findViewById(R.id.lblNombre);
        ImageView userImage = header.findViewById(R.id.profile_image);

        txtNombre.setText(bundle.getString("NOMBRE"));
        try{
            Glide.with(header)
                    .load(bundle.getString("IMAGEN"))
                    .error(R.drawable.user)
                    .into(userImage);
        }catch(Exception e){
            e.printStackTrace();
        }

        Menu menu = navigationView.getMenu();
        String rol = bundle.getString("ROL");
        if (rol.equals("U1")) {
            menu.findItem(R.id.op3).setVisible(false);
        }
        if (rol.equals("U2")) {
            menu.findItem(R.id.op2).setVisible(false);
        }
    }
}