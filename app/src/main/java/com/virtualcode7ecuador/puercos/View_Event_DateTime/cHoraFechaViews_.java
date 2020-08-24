package com.virtualcode7ecuador.puercos.View_Event_DateTime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.Calendar;

public class cHoraFechaViews_
{
    private String CERO="0";
    private String BARRA="-";
    private Context context;
    public final Calendar c = Calendar.getInstance();
    private static final String DOS_PUNTOS = ":";
    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    private TextView textInputEditText_fecha;
    private TextView textInputEditText_hora;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private int identifi;

    public cHoraFechaViews_(Context context1)
    {
        this.context = context1;
    }
    public Context getContext() {
        return context;
    }

    public int getIdentifi() {
        return identifi;
    }

    public void setIdentifi(int identifi) {
        this.identifi = identifi;
    }

    public TextView getTextInputEditText_fecha() {
        return textInputEditText_fecha;
    }

    public void setTextInputEditText_fecha(TextView textInputEditText_fecha) {
        this.textInputEditText_fecha = textInputEditText_fecha;
    }

    public TextView getTextInputEditText_hora() {
        return textInputEditText_hora;
    }

    public void setTextInputEditText_hora(TextView textInputEditText_hora) {
        this.textInputEditText_hora = textInputEditText_hora;
    }

    public DatePickerDialog getDatePickerDialog() {
        return datePickerDialog;
    }

    public void setDatePickerDialog(DatePickerDialog datePickerDialog) {
        this.datePickerDialog = datePickerDialog;
    }

    public TimePickerDialog getTimePickerDialog() {
        return timePickerDialog;
    }

    public void setTimePickerDialog(TimePickerDialog timePickerDialog) {
        this.timePickerDialog = timePickerDialog;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void obtener_FechaPicker()
    {
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
            {
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                textInputEditText_fecha.setText(year + BARRA + mesFormateado + BARRA + dayOfMonth);
            }
        },anio,mes,dia);
        datePickerDialog.show();
    }
    public void obtener_TimePicker()
    {
        timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
            {
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                //Muestro la hora con el formato deseado
                textInputEditText_hora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
        },hora,minuto,true);
        timePickerDialog.show();
    }

    public void obtener_FechaPicker2(final TextView textView)
    {
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
            {
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                textView.setText(year + BARRA + mesFormateado + BARRA + dayOfMonth);
            }
        },anio,mes,dia);
        datePickerDialog.show();
    }
    public void obtener_TimePicker2(final TextView textView_)
    {
        timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute)
            {
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                //Muestro la hora con el formato deseado
                textView_.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
            }
        },hora,minuto,true);
        timePickerDialog.show();
    }
}
