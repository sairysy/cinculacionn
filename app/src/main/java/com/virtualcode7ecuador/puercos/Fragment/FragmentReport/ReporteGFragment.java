package com.virtualcode7ecuador.puercos.Fragment.FragmentReport;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.virtualcode7ecuador.puercos.Adapter.cAdapterReport;
import com.virtualcode7ecuador.puercos.POO.cTotReport;
import com.virtualcode7ecuador.puercos.R;
import com.virtualcode7ecuador.puercos.View_Event_DateTime.cHoraFechaViews_;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class ReporteGFragment extends Fragment
{
    private Button button_listar;
    private View view_;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;

    private TextView textView_fecha_ini;
    private TextView textView_fecha_fin;
    private TextView textView_hora_ini;
    private TextView textView_hora_fin;

    private LinearLayoutCompat linearLayoutCompat;
    private TextView textView_total;
    private TextView textView_asistidos;
    private TextView textView_ausentes_;
    private TextView textView_restantes;

    private static final String CERO = "0";
    private static final String BARRA = "-";

    private cHoraFechaViews_ OhoraFechaViews_;

    private ProgressDialog progressDialog;
    private ArrayList<cTotReport> totReportArrayList;
    private RecyclerView recyclerView_reportG;

    public ReporteGFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_ = inflater.inflate(R.layout.fragment_reporte_g, container, false);
        button_listar = view_.findViewById(R.id.btn_listar_);

        OhoraFechaViews_ = new cHoraFechaViews_(getContext());

        textView_fecha_ini = view_.findViewById(R.id._id_fecha_cita_inicio);
        textView_fecha_fin =  view_.findViewById(R.id._id_fecha_cita_fin);
        textView_hora_ini = view_.findViewById(R.id._id_hora_cita_inicio);
        textView_hora_fin = view_.findViewById(R.id._id_hora_cita_fin);

        linearLayoutCompat = view_.findViewById(R.id.linearlayout_reportG);


        textView_total = view_.findViewById(R.id.text_view_sumTotal_reportg);
        textView_asistidos = view_.findViewById(R.id.textview_asistido_reporte_G);
        textView_ausentes_ = view_.findViewById(R.id.textview_ausentes_report_G);
        textView_restantes = view_.findViewById(R.id.textview_pendientes_);

        recyclerView_reportG = view_.findViewById(R.id.recyclerview_reportes_general);

        textView_fecha_ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OhoraFechaViews_.obtener_FechaPicker2(textView_fecha_ini);
            }
        });

        textView_fecha_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OhoraFechaViews_.obtener_FechaPicker2(textView_fecha_fin);
            }
        });

        textView_hora_ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OhoraFechaViews_.obtener_TimePicker2(textView_hora_ini);
            }
        });

        textView_hora_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OhoraFechaViews_.obtener_TimePicker2(textView_hora_fin);
            }
        });
        totReportArrayList = new ArrayList<>();
        obtenerfechaActual();
        webserviceReportG();
        button_listar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                webserviceReportG();
            }
        });
        return view_;
    }
    private void obtenerfechaActual()
    {
        Calendar calendar= Calendar.getInstance(TimeZone.getDefault());
        int year=calendar.get(Calendar.YEAR);
        final int mesActual = calendar.get(Calendar.MONTH)+1;
        String diaFormateado = (calendar.get(Calendar.DAY_OF_MONTH) < 10)? CERO + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)):String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
        textView_fecha_ini.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
        textView_fecha_fin.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
        //stringFecha=year + BARRA + mesFormateado + BARRA + diaFormateado;
        textView_hora_ini.setText("06:00");
        textView_hora_fin.setText("20:00");
    }
    private void webserviceReportG()
    {
        crearProgressDialog();
        String url = getContext().getString(R.string.url_read_reportG)+"?fechaini="+textView_fecha_ini.getText().toString()+
                "&fechafin="+textView_fecha_fin.getText().toString()+"&horaini="+textView_hora_ini.getText().toString()+
                "&horafin="+textView_hora_fin.getText().toString();
        Log.e("url_report_G",url);
       jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response)
           {
               for (int i=0;i<response.length();i++)
               {
                   try
                   {
                       JSONObject jsonObject = response.getJSONObject(i);
                       cTotReport OtotReport = new cTotReport();
                       OtotReport.setActividad(jsonObject.getString("actividad"));
                       OtotReport.setAsistidos(jsonObject.getInt("asistido"));
                       OtotReport.setAusentes(jsonObject.getInt("ausente"));
                       OtotReport.setEspera(jsonObject.getInt("restante"));
                       OtotReport.setTot(jsonObject.getInt("total"));
                       totReportArrayList.add(OtotReport);
                   } catch (JSONException e)
                   {
                       Toast.makeText(getContext(), "TRYCATCH : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                       cerrarProgresDialog();
                   }
                   if (totReportArrayList.size()>0)
                   {
                       llenarRecyclerView(totReportArrayList);
                   }
               }
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error)
           {
               cerrarProgresDialog();
               Snackbar snackbar = Snackbar.make(linearLayoutCompat,"LISTA VACIA",Snackbar.LENGTH_LONG);
               snackbar.show();
           }
       });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void llenarRecyclerView(ArrayList<cTotReport> totReportArrayList)
    {
        cAdapterReport adapterReport = new cAdapterReport(R.layout.view_datos_report_general,totReportArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_reportG.setLayoutManager(linearLayoutManager);
        recyclerView_reportG.setAdapter(adapterReport);
        cerrarProgresDialog();
    }

    private void cerrarProgresDialog()
    {
        progressDialog.cancel();
        progressDialog.dismiss();
    }
    private void crearProgressDialog()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("GENERANDO INFORME....");
        progressDialog.setMessage("Porfavor espere...");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();
    }
}