package com.virtualcode7ecuador.puercos.WebServices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.virtualcode7ecuador.puercos.Adapter.cAdapterCitas;
import com.virtualcode7ecuador.puercos.POO.cCita;
import com.virtualcode7ecuador.puercos.R;
import com.virtualcode7ecuador.puercos.Views.AgendarCitaActivity;
import com.virtualcode7ecuador.puercos.Views.SeguimientoCitaActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import static com.virtualcode7ecuador.puercos.LoginActivity.Ousuario;
import static com.virtualcode7ecuador.puercos.LoginActivity.arrayList;
import static com.virtualcode7ecuador.puercos.LoginActivity.recyclerView;

public class cCitasService implements Serializable
{
    private Context context_;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private int Result_activity_seguimiento=120;
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
                "&id_estado_cita=3&doctor="+Ocita.getDoctor()+"&especialidad="+Ocita.getEspecialidad_();
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
    public void AgendarSeguimiento(cCita Ocita  )
    {
        abrirProgressDialog("GUARDANDO","Porfavor espere...");
        String url_ = getContext_()
                .getString(R.string.url_insert_seguimiento)+"?url_foto_cita="+Ocita.getUrl_foto_cita()+
                "&url_foto_cita_seg="+Ocita.getUrl_foto_seguimi()+
                "&fecha_cita="+Ocita.getFecha_cita()+"&hora_cita="+Ocita.getHora_cita()+
                "&actividad_cita="+Ocita.getActividad_cita()+"&detalle_cita="+Ocita.getDetalle_cita()+
                "&id_estado_cita=3&doctor="+Ocita.getDoctor()+"&especialidad="+Ocita.getEspecialidad_();
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
    public void readCitas(int tipo_cita, String fecha_ini, String fecha_fin, String hora_ini
            , String hora_fin)
    {
        abrirProgressDialog("SEGUIMIENTO","Porfavor espere...");
        String url_="";
        if(Ousuario.getId_rol_usuario()!=2)
        {
            url_ = getContext_().getString(R.string.url_read_seguimiento)+"?estado_cita="+tipo_cita+"&fecha_inicial="
                    +fecha_ini+"&fecha_fin="+fecha_fin+"&hora_ini="+hora_ini+"&hora_fin="+hora_fin;
        }else
            {
                url_ = getContext_().getString(R.string.url_read_citas)+"?estado_cita="+tipo_cita+"&fecha_inicial="
                        +fecha_ini+"&fecha_fin="+fecha_fin+"&hora_ini="+hora_ini+"&hora_fin="+hora_fin;
            }
        Log.e("URLREADCITAS",url_);
        jsonObjectRequest = new JsonObjectRequest(url_
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("status").equals("ok"))
                    {
                        JSONArray  jsonArray = response.getJSONArray("datos");
                        if (arrayList!=null && arrayList.size()>0){arrayList.clear();}
                        for (int ih=0;ih<jsonArray.length();ih++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(ih);
                            cCita Oc = new cCita();
                            Oc.setUrl_foto_cita(jsonObject.getString("url_foto_cita"));
                            Oc.setDetalle_cita(jsonObject.getString("detalle_cita"));
                            Oc.setActividad_cita(jsonObject.getString("actividad_cita"));
                            //Oc.setOestadoCita(jsonObject.getInt(""));
                            Oc.setFecha_cita(jsonObject.getString("fecha_cita"));
                            Oc.setHora_cita(jsonObject.getString("hora_cita"));
                            Oc.setId_Cita(jsonObject.getInt("pk_auto_citas"));
                            Oc.setDoctor(jsonObject.getString("doctor"));
                            Oc.setEspecialidad_(jsonObject.getString("departamento"));
                            arrayList.add(Oc);
                        }
                        final cAdapterCitas Oda;
                        /*if (Ousuario.getId_rol_usuario()==2)
                        {
                             Oda = new cAdapterCitas(Ousuario.getId_rol_usuario(),
                                    R.layout.card_views_lista_seguimiento_preview_master,
                                    getContext_(),arrayList);
                        }else
                            {*/
                                Oda= new cAdapterCitas(Ousuario.getId_rol_usuario(),
                                        R.layout.card_views_lista_seguimiento_preview_master,
                                        getContext_(), arrayList, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        cCita Oc =  arrayList.get(recyclerView.getChildAdapterPosition(view));
                                        //Toast.makeText(context_, "c", Toast.LENGTH_SHORT).show();
                                        if(Ousuario.getId_rol_usuario()==2)
                                        {
                                            abrirActivityAgendar(Oc);
                                        }else
                                            {
                                                abrirActivitySeguimiento(Oc);
                                            }
                                    }
                               });
                            //}
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext_());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        Oda.notifyDataSetChanged();
                        recyclerView.setAdapter(Oda);
                        cerrarProgresDialog();
                    }else
                        {
                            cerrarProgresDialog();
                            Toast.makeText(getContext_(), "DATOS VACIOS..", Toast.LENGTH_SHORT).show();
                        }
                } catch (JSONException e) {
                    cerrarProgresDialog();
                    Toast.makeText(getContext_(), "TRYCATH ERROR: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context_, "VOLLEY ERROR : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                cerrarProgresDialog();
            }
        });
        requestQueue = Volley.newRequestQueue(getContext_());
        requestQueue.add(jsonObjectRequest);
    }

    private void abrirActivityAgendar(cCita oc)
    {
        Intent intent = new Intent(getContext_(), AgendarCitaActivity.class);
        intent.putExtra("url_img",oc.getUrl_foto_cita());
        intent.putExtra("id_key",oc.getId_Cita());
        intent.putExtra("doctor",oc.getDoctor());
        intent.putExtra("actividad",oc.getActividad_cita());
        getContext_().startActivity(intent);
    }

    public void update_cita_seguimiento(final int id_auto_primaty_key_cita, String url_seguimiento, int estado_, final SeguimientoCitaActivity seguimientoCitaActivity)
    {
        abrirProgressDialog("ACTUALIZANDO","Porfavor espere....");
        String url_=getContext_().getString(R.string.url_update_estado_citas)
                +"?key_cita="+id_auto_primaty_key_cita+"&url_foto_seg="
                +url_seguimiento+"&estado_cita="+estado_;
        jsonObjectRequest = new JsonObjectRequest(url_, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {
                    if (response.getString("status").equals("ok"))
                    {
                        Toast.makeText(getContext_(), "OK...", Toast.LENGTH_SHORT).show();
                        deleteObjRecycler(id_auto_primaty_key_cita);
                        Intent data = new Intent();
                        data.setData(Uri.parse(String.valueOf(id_auto_primaty_key_cita)));
                        seguimientoCitaActivity.setResult(Result_activity_seguimiento,data);
                        seguimientoCitaActivity.finish();
                        cerrarProgresDialog();
                    }else
                        {
                            Toast.makeText(getContext_(), "ERROR AL ACTUALIZAR", Toast.LENGTH_SHORT).show();
                            cerrarProgresDialog();
                        }
                } catch (JSONException e)
                {
                    Toast.makeText(getContext_(), "TRYCATCH : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    cerrarProgresDialog();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getContext_(), "VOLLEYERROR : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                cerrarProgresDialog();
            }
        });
        requestQueue = Volley.newRequestQueue(getContext_());
        requestQueue.add(jsonObjectRequest);
    }
    private void abrirActivitySeguimiento(cCita oc)
    {
        Intent intent = new Intent(getContext_(), SeguimientoCitaActivity.class);
        intent.putExtra("id_auto_cita",oc.getId_Cita());
        intent.putExtra("url_img",oc.getUrl_foto_cita());
        intent.putExtra("actividad",oc.getActividad_cita());
        intent.putExtra("detalle",oc.getDetalle_cita());
        intent.putExtra("fecha",oc.getFecha_cita());
        intent.putExtra("hora",oc.getHora_cita());
        intent.putExtra("departamento",oc.getEspecialidad_());
        intent.putExtra("doctor",oc.getDoctor());
        getContext_().startActivity(intent);
    }
    public void deleteObjRecycler(int id)
    {
        if (arrayList!=null)
        {
            for (int i=0;i<arrayList.size();i++)
            {
                if (arrayList.get(i).getId_Cita()==id)
                {
                    arrayList.remove(i);
                }
            }
            cAdapterCitas Oda= new cAdapterCitas(Ousuario.getId_rol_usuario(),
                    R.layout.card_views_lista_seguimiento_preview_master,
                    getContext_(), arrayList, new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    cCita Oc =  arrayList.get(recyclerView.getChildAdapterPosition(view));
                    //Toast.makeText(context_, "c", Toast.LENGTH_SHORT).show();
                    if(Ousuario.getId_rol_usuario()==2)
                    {
                        abrirActivityAgendar(Oc);
                    }else
                    {
                        abrirActivitySeguimiento(Oc);
                    }
                }
            });
            //}
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext_());
            recyclerView.setLayoutManager(linearLayoutManager);
            Oda.notifyDataSetChanged();
            recyclerView.setAdapter(Oda);
        }
    }
}
