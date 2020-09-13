package com.virtualcode7ecuador.puercos.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuador.puercos.POO.cCita;
import com.virtualcode7ecuador.puercos.R;

import java.util.ArrayList;

public class cAdapterMisCitas extends RecyclerView.Adapter<cAdapterMisCitas.cViewHolderMisCitas>
{
    private ArrayList<cCita> mCitaArrayList;
    private Context mContext;

    public cAdapterMisCitas(ArrayList<cCita> mCitaArrayList, Context mContext) {
        this.mCitaArrayList = mCitaArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public cViewHolderMisCitas onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mis_citas,parent,false);
        return new cViewHolderMisCitas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cViewHolderMisCitas holder, int position)
    {
        Picasso.with(mContext).load(mCitaArrayList.get(position).getUrl_foto_cita())
                .error(R.drawable.error_image_load).into(holder.mImageView);
        holder.mTextViewFechaHora.setText(mCitaArrayList.get(position).getFecha_cita()+" "+mCitaArrayList.get(position).getHora_cita());
        holder.mTextViewDoctor.setText(mCitaArrayList.get(position).getDoctor());
        holder.mTextViewDepartamento.setText(mCitaArrayList.get(position).getEspecialidad_());
        holder.mTextViewActividad.setText(mCitaArrayList.get(position).getActividad_cita());
    }

    @Override
    public int getItemCount() {
        return mCitaArrayList.size();
    }


    public class cViewHolderMisCitas extends RecyclerView.ViewHolder
    {
        ImageView mImageView;
        TextView mTextViewFechaHora;
        TextView mTextViewActividad;
        TextView mTextViewDoctor;
        TextView mTextViewDepartamento;
        public cViewHolderMisCitas(@NonNull View itemView)
        {
            super(itemView);
            mImageView = itemView.findViewById(R.id.id_cardmiscitas_imageview);
            mTextViewFechaHora = itemView.findViewById(R.id.id_cardmiscitas_fecha_hora);
            mTextViewActividad = itemView.findViewById(R.id.id_cardmiscitas_actividad);
            mTextViewDoctor = itemView.findViewById(R.id.id_cardmiscitas_doctor);
            mTextViewDepartamento = itemView.findViewById(R.id.id_cardmiscitas_departamento);
        }
    }
}
