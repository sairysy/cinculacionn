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
import com.virtualcode7ecuador.puercos.FragmentMaster.CreateUserFragment;
import com.virtualcode7ecuador.puercos.FragmentMaster.FragmentReport.ReportSeguimientoFragment;
import com.virtualcode7ecuador.puercos.FragmentsAgendador.AgendarCitaFragment;
import com.virtualcode7ecuador.puercos.LoginActivity;
import com.virtualcode7ecuador.puercos.R;

import static com.virtualcode7ecuador.puercos.LoginActivity.Ousuario;

public class InicioMasterActivity extends AppCompatActivity
{
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private ReportSeguimientoFragment OreportSeguimientoFragment;
    private CreateUserFragment OcreateUserFragment;
    private AgendarCitaFragment OagendarCitaFragment;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_master);
        drawerLayout = findViewById(R.id.id_drawer_layout);
        navigationView = findViewById(R.id.id_navigation_view);
        toolbar = findViewById(R.id.toolbar_);
        configurarToolbarHamborguer();
        fragmentManager = getSupportFragmentManager();
        OreportSeguimientoFragment = new ReportSeguimientoFragment();
        OcreateUserFragment = new CreateUserFragment();
        OagendarCitaFragment = new AgendarCitaFragment();
        configurarDrawer();
        llenarFragmentDefecto();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.id_seguimiento_master:
                        fragmentManager.beginTransaction().replace(R.id.fragment_container_master
                                ,OreportSeguimientoFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.id_new_user_master:
                        if (Ousuario.getId_rol_usuario()==3)
                        {
                            fragmentManager.beginTransaction().replace(R.id.fragment_container_master
                                    ,OcreateUserFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                        }else
                            {
                                Toast.makeText(InicioMasterActivity.this, "NO POSEE PERMISOS ....", Toast.LENGTH_SHORT).show();
                            }
                        break;
                    case R.id.id_subir_master:
                        fragmentManager.beginTransaction().replace(R.id.fragment_container_master
                                ,OagendarCitaFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(InicioMasterActivity.this);
        builder.setTitle("FINALIZAR SESION");
        builder.setMessage("DESEA CERRAR SESSION ?");
        builder.setPositiveButton("SALIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(InicioMasterActivity.this, "ADIOS ....",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InicioMasterActivity.this, LoginActivity.class);
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(InicioMasterActivity
                .this,drawerLayout,toolbar,R.string.vacio,R.string.vacio);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void llenarFragmentDefecto()
    {
        fragmentManager.beginTransaction().add(R.id.fragment_container_master,OreportSeguimientoFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed()
    {
        return;
    }
}