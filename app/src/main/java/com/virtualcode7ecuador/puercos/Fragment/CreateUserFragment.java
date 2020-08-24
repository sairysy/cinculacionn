package com.virtualcode7ecuador.puercos.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.virtualcode7ecuador.puercos.POO.cRolUser;
import com.virtualcode7ecuador.puercos.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.virtualcode7ecuador.puercos.LoginActivity.OrolUserArrayList;

public class CreateUserFragment extends Fragment implements View.OnClickListener
{
    private View view_;
    private AutoCompleteTextView autoCompleteTextView_;
    private Button button_aceptar;
    private Button button_cancelar;
    private TextInputEditText textInputEditText_user;
    private TextInputEditText textInputEditText_pass;
    public CreateUserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_ = inflater.inflate(R.layout.fragment_create_user, container, false);
        autoCompleteTextView_= view_.findViewById(R.id.auto_complete_rol);
        textInputEditText_pass=view_.findViewById(R.id.textview_password_);
        textInputEditText_user=view_.findViewById(R.id.textview_usuario_);
        button_cancelar = view_.findViewById(R.id.id_btn_cancelar_registro_new_user);
        button_aceptar = view_.findViewById(R.id.id_btn_guardar_registro_new_user);
        button_cancelar.setOnClickListener(this);
        button_aceptar.setOnClickListener(this);
        llenarTextviewAutoComplete();
        return view_;
    }

    private void llenarTextviewAutoComplete()
    {
        ArrayAdapter<cRolUser>arrayAdapter = new ArrayAdapter<cRolUser>(getContext(),R.layout.support_simple_spinner_dropdown_item,OrolUserArrayList);
        autoCompleteTextView_.setAdapter(arrayAdapter);
        autoCompleteTextView_.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                cRolUser Or = (cRolUser)adapterView.getItemAtPosition(i);
                Toast.makeText(getContext(), "idRol : "+Or.getId_auto_rol(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.id_btn_cancelar_registro_new_user:
                textInputEditText_user.setText("");
                textInputEditText_pass.setText("");
                Toast.makeText(getContext(), "OPERACION CANCELADA...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_btn_guardar_registro_new_user:
                webserviceGuardar();
                break;
        }
    }

    private void webserviceGuardar()
    {
        int rol=0;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        if (autoCompleteTextView_.getText().toString().equals("AGENDADOR"))
        {
            rol = 1;
        }
        if (autoCompleteTextView_.getText().toString().equals("ADMIN"))
        {
            rol = 2;
        }
        if (autoCompleteTextView_.getText().toString().equals("MASTER"))
        {
            rol = 3;
        }
        String url_=getContext().getString(R.string.url_create_user)+"?user="+textInputEditText_user.getText().toString()
                +"&pass="+textInputEditText_pass.getText().toString()+"&rol="+rol;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url_, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("status").equals("ok"))
                    {
                        Toast.makeText(getContext(), "USUARIO GUARDADO...", Toast.LENGTH_SHORT).show();
                    }else
                        {
                            Toast.makeText(getContext(), "ERROR AL GUARDAR", Toast.LENGTH_SHORT).show();
                        }
                } catch (JSONException e)
                {
                    Toast.makeText(getContext(), "TRYCATH : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(), "ERROR "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}