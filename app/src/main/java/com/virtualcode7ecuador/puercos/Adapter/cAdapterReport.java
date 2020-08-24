package com.virtualcode7ecuador.puercos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.virtualcode7ecuador.puercos.POO.cTotReport;
import com.virtualcode7ecuador.puercos.R;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class cAdapterReport extends RecyclerView.Adapter<cAdapterReport.cViewHolderReport>
{
    private int resource_;
    private ArrayList<cTotReport> totReportArrayList;
    public cAdapterReport(int res,ArrayList<cTotReport> OTotReports)
    {
        this.totReportArrayList = OTotReports;
        this.resource_ = res;
    }

    @NonNull
    @Override
    public cViewHolderReport onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource_,parent,false);
        return new cViewHolderReport(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderReport holder, int position)
    {
        holder.textView_espera.setText(String.valueOf(totReportArrayList.get(position).getEspera()));
        holder.textView_actividad.setText(totReportArrayList.get(position).getActividad());
        holder.textView_ausentes.setText(String.valueOf(totReportArrayList.get(position).getAusentes()));
        holder.textView_asistidod.setText(String.valueOf(totReportArrayList.get(position).getAsistidos()));
        holder.textView_total.setText(String.valueOf(totReportArrayList.get(position).getTot()));
    }

    @Override
    public int getItemCount()
    {
        return totReportArrayList.size();
    }

    class cViewHolderReport extends RecyclerView.ViewHolder
    {
        TextView textView_actividad;
        TextView textView_total;
        TextView textView_asistidod;
        TextView textView_ausentes;
        TextView textView_espera;
        public cViewHolderReport(@NonNull View itemView)
        {
            super(itemView);
            textView_actividad = itemView.findViewById(R.id.textview_actividad_report_G);
            textView_total = itemView.findViewById(R.id.text_view_sumTotal_reportg);
            textView_asistidod = itemView.findViewById(R.id.textview_asistido_reporte_G);
            textView_ausentes = itemView.findViewById(R.id.textview_ausentes_report_G);
            textView_espera = itemView.findViewById(R.id.textview_pendientes_);
        }
    }
}
