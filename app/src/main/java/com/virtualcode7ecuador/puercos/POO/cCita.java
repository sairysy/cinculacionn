package com.virtualcode7ecuador.puercos.POO;

import java.util.Date;
import java.util.Timer;

public class cCita
{
    private int id_Cita;
    private String url_foto_cita;
    private String url_foto_seguimi;
    private String fecha_cita;
    private String hora_cita;
    private String actividad_cita;
    private String detalle_cita;
    private cEstadoCita OestadoCita;
    private String doctor;
    private String especialidad_;

    public cCita(){}

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getEspecialidad_() {
        return especialidad_;
    }

    public void setEspecialidad_(String especialidad_) {
        this.especialidad_ = especialidad_;
    }

    public int getId_Cita() {
        return id_Cita;
    }

    public void setId_Cita(int id_Cita) {
        this.id_Cita = id_Cita;
    }

    public String getUrl_foto_cita() {
        return url_foto_cita;
    }

    public void setUrl_foto_cita(String url_foto_cita) {
        this.url_foto_cita = url_foto_cita;
    }

    public String getUrl_foto_seguimi() {
        return url_foto_seguimi;
    }

    public void setUrl_foto_seguimi(String url_foto_seguimi) {
        this.url_foto_seguimi = url_foto_seguimi;
    }

    public String getFecha_cita() {
        return fecha_cita;
    }

    public void setFecha_cita(String fecha_cita) {
        this.fecha_cita = fecha_cita;
    }

    public String getHora_cita() {
        return hora_cita;
    }

    public void setHora_cita(String hora_cita) {
        this.hora_cita = hora_cita;
    }

    public String getActividad_cita() {
        return actividad_cita;
    }

    public void setActividad_cita(String actividad_cita) {
        this.actividad_cita = actividad_cita;
    }

    public String getDetalle_cita() {
        return detalle_cita;
    }

    public void setDetalle_cita(String detalle_cita) {
        this.detalle_cita = detalle_cita;
    }

    public cEstadoCita getOestadoCita() {
        return OestadoCita;
    }

    public void setOestadoCita(cEstadoCita oestadoCita) {
        OestadoCita = oestadoCita;
    }
}
