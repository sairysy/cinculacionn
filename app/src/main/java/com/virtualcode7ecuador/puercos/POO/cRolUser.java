package com.virtualcode7ecuador.puercos.POO;

public class cRolUser
{
    private int id_auto_rol;
    private String detalle_rol_user;
    public cRolUser(){}

    public int getId_auto_rol() {
        return id_auto_rol;
    }

    public void setId_auto_rol(int id_auto_rol) {
        this.id_auto_rol = id_auto_rol;
    }

    public String getDetalle_rol_user() {
        return detalle_rol_user;
    }

    public void setDetalle_rol_user(String detalle_rol_user) {
        this.detalle_rol_user = detalle_rol_user;
    }

    @Override
    public String toString() {
        return detalle_rol_user;
    }
}
