package com.virtualcode7ecuador.puercos.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.virtualcode7ecuador.puercos.LoginActivity;
import com.virtualcode7ecuador.puercos.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateUserLoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private View view_;
    private AutoCompleteTextView autoCompleteTextView_;
    private Button button_aceptar;
    private Button button_cancelar;
    private TextInputEditText textInputEditText_user;
    private TextInputEditText textInputEditText_pass;

    private TextInputEditText mTextViewName;
    private TextInputEditText mTextViewTelefono;
    private TextInputEditText mTextViewDni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_login);
        toolbar = findViewById(R.id.toolbar_);
        mTextViewDni = findViewById(R.id.id_edittext_cedula);
        mTextViewName = findViewById(R.id.id_edittext_name);
        mTextViewTelefono = findViewById(R.id.id_edittext_telefono);
        autoCompleteTextView_= findViewById(R.id.auto_complete_rol);
        textInputEditText_pass= findViewById(R.id.textview_password_);
        textInputEditText_user= findViewById(R.id.textview_usuario_);
        button_cancelar = findViewById(R.id.id_btn_cancelar_registro_new_user);
        button_aceptar = findViewById(R.id.id_btn_guardar_registro_new_user);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("REGISTRO USUARIO");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorSecondaryText));
        button_cancelar.setOnClickListener(this);
        button_aceptar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.id_btn_cancelar_registro_new_user:
                textInputEditText_user.setText("");
                textInputEditText_pass.setText("");
                Toast.makeText(CreateUserLoginActivity.this, "OPERACION CANCELADA...", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(CreateUserLoginActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.id_btn_guardar_registro_new_user:
                if (!textInputEditText_pass.getText().toString().isEmpty() || !textInputEditText_user.getText().toString().isEmpty())
                {
                    webserviceGuardar();
                }else
                    {
                        Toast.makeText(this, "DATOS VACIOS...", Toast.LENGTH_SHORT).show();
                    }
                break;
        }
    }

    private void webserviceGuardar()
    {
        progressDialog = new ProgressDialog(CreateUserLoginActivity.this);
        progressDialog.setTitle("REGISTRANDO USUARIO");
        progressDialog.setMessage("Porfavor espere....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        int rol=2;
        RequestQueue requestQueue = Volley.newRequestQueue(CreateUserLoginActivity.this);

        String url_=this.getString(R.string.url_create_user)+"?user="+textInputEditText_user.getText().toString()
                +"&pass="+textInputEditText_pass.getText().toString()+"&rol="+rol+"&nombres="+mTextViewName.getText().toString()+
                "&telefono="+mTextViewTelefono.getText().toString()+
                "&dni="+mTextViewDni.getText().toString();


        /*String url_=getString(R.string.url_create_user)+"?user="+textInputEditText_user.getText().toString()
                +"&pass="+textInputEditText_pass.getText().toString()+"&rol="+rol;*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url_, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("status").equals("ok"))
                    {
                        Toast.makeText(CreateUserLoginActivity.this, "USUARIO GUARDADO...", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        progressDialog.dismiss();
                        onBackPressed();
                    }else
                    {
                        Toast.makeText(CreateUserLoginActivity.this, "ERROR AL GUARDAR", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e)
                {
                    Toast.makeText(CreateUserLoginActivity.this, "TRYCATH : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(CreateUserLoginActivity.this, "ERROR "+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();
                progressDialog.dismiss();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        Intent intent = new Intent(CreateUserLoginActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}