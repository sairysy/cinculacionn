package com.virtualcode7ecuador.puercos.Fragment.FragmentReport;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualcode7ecuador.puercos.R;
import com.virtualcode7ecuador.puercos.View_Event_DateTime.cHoraFechaViews_;
import com.virtualcode7ecuador.puercos.WebServices.cCitasService;

import java.util.Calendar;
import java.util.TimeZone;

import static android.app.Activity.RESULT_OK;
import  static com.virtualcode7ecuador.puercos.LoginActivity.recyclerView;
public class ReportSeguimientoFragment extends Fragment implements View.OnClickListener
{
    private static final String CERO = "0";
    private static final String BARRA = "-";
    private cCitasService OcitasService;
    private View view_;
    private TextView textView_fecha_inicio;
    private TextView textView_fecha_fin;
    private TextView textView_hora_inicio;
    private TextView textView_hora_fin;
    private Button button_buscar;
    private cHoraFechaViews_ OhoraFechaViews_;
    int Result_activity_seguimiento=120;
    public ReportSeguimientoFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view_ = inflater.inflate(R.layout.fragment_report, container, false);
        textView_fecha_inicio = view_.findViewById(R.id._id_fecha_cita_inicio);
        textView_fecha_fin = view_.findViewById(R.id._id_fecha_cita_fin);
        textView_hora_inicio = view_.findViewById(R.id._id_hora_cita_inicio);
        textView_hora_fin = view_.findViewById(R.id._id_hora_cita_fin);
        button_buscar = view_.findViewById(R.id._id_btn_buscar_citas_reportes);
        recyclerView = view_.findViewById(R.id.recyclerview_reportes_fechas_hora_estado);
        OhoraFechaViews_ = new cHoraFechaViews_(getContext());
        OcitasService = new cCitasService(getContext());
        llenarvaloresdefectoHoy();
        addeventosOnclickTextview();
        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OcitasService.readCitas(3,textView_fecha_inicio.getText().toString()
                        ,textView_fecha_fin.getText().toString()
                        ,textView_hora_inicio.getText().toString()
                        ,textView_hora_fin.getText().toString());
            }
        });
        return view_;
    }

    private void llenarvaloresdefectoHoy()
    {
        obtenerfechaActual();
        OcitasService.readCitas(3,textView_fecha_inicio.getText().toString()
                ,textView_fecha_fin.getText().toString(),"05:00:00","20:00:00");
    }
    private void obtenerfechaActual()
    {
        Calendar calendar= Calendar.getInstance(TimeZone.getDefault());
        int year=calendar.get(Calendar.YEAR);
        final int mesActual = calendar.get(Calendar.MONTH)+1;
        String diaFormateado = (calendar.get(Calendar.DAY_OF_MONTH) < 10)? CERO + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)):String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
        textView_fecha_inicio.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
        textView_fecha_fin.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);
        //stringFecha=year + BARRA + mesFormateado + BARRA + diaFormateado;
    }
    private void addeventosOnclickTextview()
    {
        textView_fecha_fin.setOnClickListener(this);
        textView_fecha_inicio.setOnClickListener(this);
        textView_hora_inicio.setOnClickListener(this);
        textView_hora_fin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id._id_fecha_cita_inicio:
                OhoraFechaViews_.obtener_FechaPicker2(textView_fecha_inicio);
                break;
            case R.id._id_fecha_cita_fin:
                OhoraFechaViews_.obtener_FechaPicker2(textView_fecha_fin);
                break;
            case R.id._id_hora_cita_inicio:
                OhoraFechaViews_.obtener_TimePicker2(textView_hora_inicio);
                break;
            case R.id._id_hora_cita_fin:
                OhoraFechaViews_.obtener_TimePicker2(textView_hora_fin);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Result_activity_seguimiento && resultCode==RESULT_OK)
        {
            Toast.makeText(getContext(), "id : "+data.getDataString(), Toast.LENGTH_SHORT).show();
            //OcitasService.deleteObjRecycler(Integer.parseInt(data.getDataString()));
            Log.e("ID : ",data.getDataString());
        }
    }
}