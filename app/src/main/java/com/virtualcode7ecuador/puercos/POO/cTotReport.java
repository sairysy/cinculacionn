package com.virtualcode7ecuador.puercos.POO;

public class cTotReport
{
    private int tot;
    private int espera;
    private int asistidos;
    private int ausentes;
    private String Actividad;

    public cTotReport(){}

    public int getTot() {
        return tot;
    }

    public void setTot(int tot) {
        this.tot = tot;
    }

    public int getEspera() {
        return espera;
    }

    public void setEspera(int espera) {
        this.espera = espera;
    }

    public int getAsistidos() {
        return asistidos;
    }

    public void setAsistidos(int asistidos) {
        this.asistidos = asistidos;
    }

    public int getAusentes() {
        return ausentes;
    }

    public void setAusentes(int ausentes) {
        this.ausentes = ausentes;
    }

    public String getActividad() {
        return Actividad;
    }

    public void setActividad(String actividad) {
        Actividad = actividad;
    }
}
