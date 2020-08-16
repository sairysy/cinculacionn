package com.virtualcode7ecuador.puercos.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuador.puercos.POO.cCita;
import com.virtualcode7ecuador.puercos.R;

import org.json.JSONException;
import org.json.JSONObject;

public class cCitasService
{
    private Context context_;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    public cCitasService(Context c)
    {
        this.context_ = c;
    }
    public cCitasService()
    {}

    public void AgendarCita(cCita Ocita)
    {
        abrirProgressDialog("GUARDANDO","Porfavor espere...");
        String url_ = getContext_()
                .getString(R.string.url_insert_cita)+"?url_foto_cita="+Ocita.getUrl_foto_cita()+
                "&url_foto_cita_seg="+Ocita.getUrl_foto_seguimi()+
                "&fecha_cita="+Ocita.getFecha_cita()+"&hora_cita="+Ocita.getHora_cita()+
                "&actividad_cita="+Ocita.getActividad_cita()+"&detalle_cita="+Ocita.getDetalle_cita()+
                "&id_estado_cita=3";
        Log.e("URLCREATECITA",url_);
        jsonObjectRequest = new JsonObjectRequest(url_
                , null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("status").equals("ok"))
                    {
                        Toast.makeText(getContext_(), "EXITO...!", Toast.LENGTH_SHORT).show();
                        cerrarProgresDialog();
                    }else
                        {
                            Toast.makeText(getContext_(), "ERROR NO SE PUDO GUARDAR...", Toast.LENGTH_LONG).show();
                            cerrarProgresDialog();
                        }
                } catch (JSONException e)
                {
                    cerrarProgresDialog();
                    Toast.makeText(getContext_(), "RESPONSEERROR : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext_(), "VolleyError : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                cerrarProgresDialog();
            }
        });
        requestQueue = Volley.newRequestQueue(getContext_());
        requestQueue.add(jsonObjectRequest);
    }
    public void UpdateEstadoCitas(cCita Ocita)
    {
        abrirProgressDialog("ACTUALIZANDO","Porfavor espere....");
        jsonObjectRequest = new JsonObjectRequest(getContext_()
                .getString(R.string.url_update_estado_citas)
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext_(), "VOLLERERROR : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                cerrarProgresDialog();
            }
        });
        requestQueue = Volley.newRequestQueue(getContext_());
        requestQueue.add(jsonObjectRequest);
    }
    private void cerrarProgresDialog()
    {
        progressDialog.cancel();
        progressDialog.dismiss();
    }

    public Context getContext_() {
        return context_;
    }

    public void setContext_(Context context_) {
        this.context_ = context_;
    }

    private void abrirProgressDialog(String title,String msm)
    {
        progressDialog = new ProgressDialog(getContext_());
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msm);
        progressDialog.setIcon(R.drawable.salud_puercos);
        progressDialog.show();
    }
}
