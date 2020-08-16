package com.virtualcode7ecuador.puercos.FragmentMaster;

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

import com.virtualcode7ecuador.puercos.POO.cRolUser;
import com.virtualcode7ecuador.puercos.R;

import static com.virtualcode7ecuador.puercos.LoginActivity.OrolUserArrayList;

public class CreateUserFragment extends Fragment implements View.OnClickListener
{
    private View view_;
    private AutoCompleteTextView autoCompleteTextView_;
    private Button button_aceptar;
    private Button button_cancelar;
    public CreateUserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_ = inflater.inflate(R.layout.fragment_create_user, container, false);
        autoCompleteTextView_= view_.findViewById(R.id.auto_complete_rol);
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
                break;
            case R.id.id_btn_guardar_registro_new_user:
                break;
        }
    }
}