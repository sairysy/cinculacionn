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

public class cAdapterCitas extends RecyclerView.Adapter<cAdapterCitas.cViewHolderCitas> implements View.OnClickListener
{
    private int id_rol_user;
    private int resource;
    private Context context;
    private ArrayList<cCita> OcitaArrayList;
    private View.OnClickListener onClickListener;
    public cAdapterCitas(int id_rol_user, int resource, Context context, ArrayList<cCita> ocitaArrayList)
    {
        this.id_rol_user = id_rol_user;
        this.resource = resource;
        this.context = context;
        OcitaArrayList = ocitaArrayList;
    }

    public cAdapterCitas(int id_rol_user, int resource, Context context, ArrayList<cCita> ocitaArrayList, View.OnClickListener onClickListener) {
        this.id_rol_user = id_rol_user;
        this.resource = resource;
        this.context = context;
        OcitaArrayList = ocitaArrayList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public cViewHolderCitas onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view_ = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
            view_.setOnClickListener(this);
        return new cViewHolderCitas(view_);
    }
    @Override
    public void onBindViewHolder(@NonNull cViewHolderCitas holder, int position)
    {
        /**PICASSO**/
        Picasso.with(context).load(OcitaArrayList.get(position).getUrl_foto_cita())
                .error(R.drawable.error_image_load).into(holder.imageView_);
        holder.textView_fecha.setText(OcitaArrayList.get(position).getFecha_cita());
        holder.textView_hora.setText(OcitaArrayList.get(position).getHora_cita());
        holder.textView_actividad.setText(OcitaArrayList.get(position).getActividad_cita());
    }
    @Override
    public int getItemCount()
    {
        return OcitaArrayList.size();
    }
    @Override
    public void onClick(View view)
    {
        if (onClickListener!=null){onClickListener.onClick(view);}
    }
    class cViewHolderCitas extends RecyclerView.ViewHolder
    {
        ImageView imageView_;
        TextView textView_fecha;
        TextView textView_hora;
        TextView textView_actividad;
        public cViewHolderCitas(@NonNull View itemView)
        {
            super(itemView);
            imageView_ = itemView.findViewById(R.id.imageview_preview_foto);
            textView_fecha = itemView.findViewById(R.id.id_preview_fecha_card_);
            textView_hora = itemView.findViewById(R.id.id_preview_hora_card_);
            textView_actividad = itemView.findViewById(R.id.id_preview_actividad_card_);
        }
    }
}
