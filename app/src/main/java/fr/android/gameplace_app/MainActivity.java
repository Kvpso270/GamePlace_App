package fr.android.gameplace_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private DatabaseManager databaseManager;
    Spinner spinner;
    public static String selectLang = String.valueOf(R.string.selectLang);
    public static final String[] languages = {"Selectionnez la langue","English","Français"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseFirestore firestore;
        firestore = FirebaseFirestore.getInstance();

        FirebaseManager firebaseDatabase = new FirebaseManager(firestore,getApplicationContext());
        firebaseDatabase.SendData();
        //Gestion de la langue
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);


        databaseManager = new DatabaseManager( this);
        databaseManager.getReadableDatabase();

        databaseManager.insertCoach( "John", "Doe", "john.doe@example.com", "mypassword");
        databaseManager.insertTeam("Real Madrid", 1, "La Liga", "Madrid", "Espagne", 0, null);
        databaseManager.insertPlayer("Lionel", "Messi", 34, 1);
        databaseManager.insertMatch("2023-04-19", "Stade de France", 48.9243, 2.3600, "Avenue Jules Rimet", null);
        databaseManager.insertMatchStatistics(1, 2, 5, 60.0, 10);
        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = parent.getItemAtPosition(position).toString();

                if (selectedLang.equals("English")){
                    setLocal(MainActivity.this,"en");
                    finish();
                    startActivity(getIntent());
                }else if (selectedLang.equals("Français")){
                    setLocal(MainActivity.this,"fr");
                    finish();
                    startActivity(getIntent());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //navigationView (menu)
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        //Gere la navigation entre les fragments
        NavController navController = Navigation.findNavController(this,R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        final TextView textTitle = findViewById(R.id.textTitle);


        //update le Title de la page selon le fragment
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                textTitle.setText(destination.getLabel());
            }
        });
        databaseManager.close();
    }

    public void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }

}