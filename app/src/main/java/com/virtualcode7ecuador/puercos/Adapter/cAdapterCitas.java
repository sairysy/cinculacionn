package com.virtualcode7ecuador.puercos.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.virtualcode7ecuador.puercos.POO.cCita;
import com.virtualcode7ecuador.puercos.R;
import com.virtualcode7ecuador.puercos.Views.AgendarCitaActivity;
import com.virtualcode7ecuador.puercos.Views.SeguimientoCitaActivity;

import java.util.ArrayList;

import static com.virtualcode7ecuador.puercos.LoginActivity.Ousuario;
import static com.virtualcode7ecuador.puercos.LoginActivity.recyclerView;

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
    public void onBindViewHolder(@NonNull cViewHolderCitas holder, final int position)
    {
        /**PICASSO**/
        Picasso.with(context).load(OcitaArrayList.get(position).getUrl_foto_cita())
                .error(R.drawable.error_image_load).into(holder.imageView_);
        holder.textView_fecha.setText(OcitaArrayList.get(position).getFecha_cita());
        holder.textView_hora.setText(OcitaArrayList.get(position).getHora_cita());
        holder.textView_actividad.setText(OcitaArrayList.get(position).getActividad_cita());
        holder.button_observar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Ousuario.getId_rol_usuario()==2)
                {
                    Intent intent = new Intent(context, AgendarCitaActivity.class);
                    intent.putExtra("url_img",OcitaArrayList.get(position).getUrl_foto_cita());
                    intent.putExtra("id_key",OcitaArrayList.get(position).getId_Cita());
                    intent.putExtra("doctor",OcitaArrayList.get(position).getDoctor());
                    intent.putExtra("actividad",OcitaArrayList.get(position).getActividad_cita());

                    intent.putExtra("zona",OcitaArrayList.get(position).getZona());
                    intent.putExtra("provincia",OcitaArrayList.get(position).getProvincia());
                    intent.putExtra("distrito",OcitaArrayList.get(position).getDistrito());
                    intent.putExtra("unidad",String.valueOf(OcitaArrayList.get(position).getUnidad()));

                    intent.putExtra("fecha",OcitaArrayList.get(position).getFecha_cita());
                    intent.putExtra("hora",OcitaArrayList.get(position).getHora_cita());
                    intent.putExtra("especialidad",OcitaArrayList.get(position).getEspecialidad_());
                    intent.putExtra("detalle",OcitaArrayList.get(position).getDetalle_cita());



                    context.startActivity(intent);
                }else
                {
                    Intent intent = new Intent(context, SeguimientoCitaActivity.class);
                    intent.putExtra("id_auto_cita",OcitaArrayList.get(position).getId_Cita());
                    intent.putExtra("url_img",OcitaArrayList.get(position).getUrl_foto_cita());
                    intent.putExtra("actividad",OcitaArrayList.get(position).getActividad_cita());
                    intent.putExtra("detalle",OcitaArrayList.get(position).getDetalle_cita());
                    intent.putExtra("fecha",OcitaArrayList.get(position).getFecha_cita());
                    intent.putExtra("hora",OcitaArrayList.get(position).getHora_cita());
                    intent.putExtra("departamento",OcitaArrayList.get(position).getEspecialidad_());
                    intent.putExtra("doctor",OcitaArrayList.get(position).getDoctor());
                    intent.putExtra("zona",OcitaArrayList.get(position).getZona());
                    intent.putExtra("provincia",OcitaArrayList.get(position).getProvincia());
                    intent.putExtra("distrito",OcitaArrayList.get(position).getDistrito());
                    intent.putExtra("unidad",String.valueOf(OcitaArrayList.get(position).getUnidad()));
                    context.startActivity(intent);
                }
            }
        });
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
        Button button_observar;
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
            button_observar =itemView.findViewById(R.id.btn_observar);
        }
    }
}
