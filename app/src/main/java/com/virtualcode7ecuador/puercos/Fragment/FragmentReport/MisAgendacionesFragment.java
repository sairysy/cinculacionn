package com.virtualcode7ecuador.puercos.Fragment.FragmentReport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuador.puercos.Adapter.cAdapterMisCitas;
import com.virtualcode7ecuador.puercos.POO.cCita;
import com.virtualcode7ecuador.puercos.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.virtualcode7ecuador.puercos.LoginActivity.Ousuario;

public class MisAgendacionesFragment extends Fragment
{
    private cAdapterMisCitas mAdapterMisCitas;
    private ArrayList<cCita> mCitaArrayList;
    private RecyclerView mRecyclerView;
    private View view_;

    private JsonObjectRequest mJsonObjectRequest;
    private RequestQueue mRequestQueue;

    public MisAgendacionesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_ =  inflater.inflate(R.layout.fragment_mis_agendaciones, container, false);
        mRecyclerView = view_.findViewById(R.id.id_recyclerview_mis_citas);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mJsonObjectRequest = new JsonObjectRequest(getContext().getResources().getString(R.string.url_mis_citas)+"?id_user="+Ousuario.getUsuario_(), null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("status").equals("ok"))
                    {
                        JSONArray jsonArray = response.getJSONArray("datos");
                        mCitaArrayList = new ArrayList<>();
                        for (int i = 0;i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            cCita oC = new cCita();
                            oC.setFecha_cita(jsonObject1.getString("fecha_cita"));
                            oC.setHora_cita(jsonObject1.getString("hora_cita"));
                            oC.setUrl_foto_cita(jsonObject1.getString("url_foto_cita"));
                            oC.setDoctor(jsonObject1.getString("doctor"));
                            oC.setEspecialidad_(jsonObject1.getString("departamento"));
                            oC.setActividad_cita(jsonObject1.getString("actividad_cita"));
                            mCitaArrayList.add(oC);
                        }
                        mAdapterMisCitas = new cAdapterMisCitas(mCitaArrayList,getContext());
                        mRecyclerView.setAdapter(mAdapterMisCitas);
                    }else
                        {
                            Toast.makeText(getContext(), "DATOS VACIOS....", Toast.LENGTH_SHORT).show();
                        }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "TRY : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext(), "VOLLEYERROR : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.add(mJsonObjectRequest);
        return view_;
    }
}