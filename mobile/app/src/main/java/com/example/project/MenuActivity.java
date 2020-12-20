package com.example.project;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.project.ui.basket.BasketFragment;
import com.example.project.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   private AppBarConfiguration mAppBarConfiguration;
   private DrawerLayout drawer;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_menu);

      Toolbar toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      drawer = findViewById(R.id.drawer_layout);
      NavigationView navigationView = findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);


//      FloatingActionButton fab = findViewById(R.id.fab);
//      fab.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            startActivity(new Intent(MenuActivity.this, CartActivity.class));
//         }
//      });


      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.setDrawerListener(toggle);

      toggle.syncState();

//      mAppBarConfiguration = new AppBarConfiguration.Builder(
//         R.id.nav_home, R.id.nav_basket, R.id.nav_slideshow, R.id.nav_share)
//         .setDrawerLayout(drawer)
//         .build();
//      NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//      NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//      NavigationUI.setupWithNavController(navigationView, navController);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu, menu);
      return true;
   }

//   @Override
//   public boolean onSupportNavigateUp() {
//      NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//      return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//         || super.onSupportNavigateUp();
//   }

   @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      int id = item.getItemId();

      switch (id){
         case R.id.nav_basket:
            startActivity(new Intent(MenuActivity.this, CartActivity.class));
            break;

         case R.id.nav_logout:
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
            finish();
            break;

         case R.id.nav_profile:
            startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
            break;
         case R.id.nav_slideshow:
            startActivity(new Intent(MenuActivity.this, CheckActivity.class));
            break;
//         case R.id.nav_home:
////            FragmentManager fm = getSupportFragmentManager();
////            HomeFragment homeFragment = new HomeFragment();
////            fm.beginTransaction().replace(R.id.drawer_layout, homeFragment).commit();
//            startActivity(new Intent(this,  MenuActivity.class);
//            break;
      }
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }
}
