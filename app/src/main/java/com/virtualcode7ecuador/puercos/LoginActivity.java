package com.virtualcode7ecuador.puercos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.virtualcode7ecuador.puercos.POO.cRolUser;
import com.virtualcode7ecuador.puercos.POO.cUsuario;
import com.virtualcode7ecuador.puercos.Runnable.cRunnableRol;
import com.virtualcode7ecuador.puercos.Views.InicioAgendadorActivity;
import com.virtualcode7ecuador.puercos.Views.InicioMasterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
{
    public static final int id_rol=0;
    public static final cUsuario Ousuario = new cUsuario();
    private Button button_login;
    private LinearLayoutCompat linearLayoutCompat;
    private TextInputEditText textInputEditText_user;
    private TextInputEditText textInputEditText_pass;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    public static final ArrayList<cRolUser>OrolUserArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textInputEditText_user = findViewById(R.id.textview_usuario_);
        textInputEditText_pass = findViewById(R.id.textview_password_);
        button_login = findViewById(R.id.id_btn_login);
        linearLayoutCompat = findViewById(R.id.Linear_layout_container_datos_login);
        llenarRoles();
    }

    private void llenarRoles()
    {
        Thread thread = new Thread(new cRunnableRol(LoginActivity.this,OrolUserArrayList));
        thread.start();
    }

    @Override
    protected void onResume()
    {
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (!textInputEditText_pass.getText().toString().isEmpty() &&
                        !textInputEditText_user.getText().toString().isEmpty())
                {
                    consumirWebservice();
                }else
                    {
                        Snackbar snackbar = Snackbar.make(linearLayoutCompat,"EXISTEN DATOS VACIOS",Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
            }
        });
        super.onResume();
    }

    private void consumirWebservice()
    {
        crearProgressDialog();
        jsonObjectRequest = new JsonObjectRequest(getString(R.string.url_login_user)+"?user="
                +textInputEditText_user.getText().toString() +"&password="+textInputEditText_pass.getText().toString()
                ,null
                , new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("status").equals("ok"))
                    {
                        Ousuario.setUsuario_(response.getString("dni_usuario"));
                        Ousuario.setPassword_(response.getString("password_usuario_"));
                        Ousuario.setId_rol_usuario(response.getInt("fk_rol_usuario"));
                        if (Ousuario.getId_rol_usuario()==2)
                        {
                            AbrirActivityUser2();
                        }else if(Ousuario.getId_rol_usuario()==1 || Ousuario.getId_rol_usuario()==3)
                        {
                            inicioActivityUser1();
                        }
                    }else if(response.getString("status").equals("error"))
                    {
                        Toast.makeText(LoginActivity.this, "Usuario no Registrado", Toast.LENGTH_LONG).show();
                    }
                    cerrarProgresDialog();
                } catch (JSONException e)
                {
                    cerrarProgresDialog();
                    Toast.makeText(LoginActivity.this, "JSONEXception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Snackbar snackbar = Snackbar.make(linearLayoutCompat,error.getMessage(),Snackbar.LENGTH_LONG);
                snackbar.show();
                cerrarProgresDialog();
            }
        });
        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void inicioActivityUser1()
    {
        Intent intent = new Intent(LoginActivity.this, InicioMasterActivity.class);
        startActivity(intent);
    }

    private void cerrarProgresDialog()
    {
        progressDialog.cancel();
        progressDialog.dismiss();
    }

    private void crearProgressDialog()
    {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIcon(R.drawable.salud_puercos);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Por favor espere ....");
        progressDialog.show();
    }

    private void AbrirActivityUser2()
    {
        Intent intent = new Intent(LoginActivity.this, InicioAgendadorActivity.class);
        startActivity(intent);
    }
}