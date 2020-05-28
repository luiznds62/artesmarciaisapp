package com.unesc.artesmarciaisapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unesc.artesmarciaisapp.ui.matriculation.MatriculationActivity;
import com.unesc.artesmarciaisapp.ui.modality.ModalityActivity;
import com.unesc.artesmarciaisapp.ui.plan.PlanActivity;
import com.unesc.artesmarciaisapp.ui.student.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_revenues, R.id.navigation_configurations)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.btnStudentScreen){
            Intent intent = new Intent(this, StudentActivity.class);
            startActivity(intent);
        }

        if(id == R.id.btnMatriculationScreen){
            Intent intent = new Intent(this, MatriculationActivity.class);
            startActivity(intent);
        }

        if(id == R.id.btnModalityScreen){
            Intent intent = new Intent(this, ModalityActivity.class);
            startActivity(intent);
        }

        if(id == R.id.btnPlanScreen){
            Intent intent = new Intent(this, PlanActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
