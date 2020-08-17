package com.virtualcode7ecuador.puercos.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.virtualcode7ecuador.puercos.FragmentMaster.FragmentReport.ReportSeguimientoFragment;
import com.virtualcode7ecuador.puercos.FragmentsAgendador.AgendarCitaFragment;
import com.virtualcode7ecuador.puercos.LoginActivity;
import com.virtualcode7ecuador.puercos.R;

public class InicioAgendadorActivity extends AppCompatActivity
{
    private AgendarCitaFragment OagendarCitaFragment;
    private FragmentManager fragmentManager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private AlertDialog alertDialog;
    private ReportSeguimientoFragment OreportSeguimientoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_agendador);
        drawerLayout = findViewById(R.id.id_drawer_layout);
        navigationView= findViewById(R.id.id_navigation_view);
        toolbar = findViewById(R.id.toolbar_);
        OagendarCitaFragment = new AgendarCitaFragment();
        OreportSeguimientoFragment = new ReportSeguimientoFragment();
        configurarToolbarHamborguer();
        fragmentManager = getSupportFragmentManager();
        configurarDrawer();
        llenarFragmentDefecto();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.id_subir_cita:
                        fragmentManager.beginTransaction().replace(R.id.fragment_container,OreportSeguimientoFragment)
                                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();
                        break;
                    case R.id.id_salir_:
                        alerDialogSalir();
                        break;
                }
                return false;
            }
        });
    }

    private void alerDialogSalir()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(InicioAgendadorActivity.this);
        builder.setTitle("FINALIZAR SESION");
        builder.setMessage("DESEA CERRAR SESSION ?");
        builder.setPositiveButton("SALIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(InicioAgendadorActivity.this, "ADIOS ....",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InicioAgendadorActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void configurarToolbarHamborguer()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configurarDrawer()
    {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(InicioAgendadorActivity
                .this,drawerLayout,toolbar,R.string.vacio,R.string.vacio);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void llenarFragmentDefecto()
    {
        fragmentManager.beginTransaction().add(R.id.fragment_container,OreportSeguimientoFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed()
    {
        return;
    }
}