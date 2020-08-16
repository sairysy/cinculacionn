package com.virtualcode7ecuador.puercos.POO;

public class cUsuario
{
    private String usuario_;
    private String password_;
    private int id_rol_usuario;
    public cUsuario(){}

    public String getUsuario_() {
        return usuario_;
    }

    public void setUsuario_(String usuario_) {
        this.usuario_ = usuario_;
    }

    public String getPassword_() {
        return password_;
    }

    public void setPassword_(String password_) {
        this.password_ = password_;
    }

    public int getId_rol_usuario() {
        return id_rol_usuario;
    }

    public void setId_rol_usuario(int id_rol_usuario) {
        this.id_rol_usuario = id_rol_usuario;
    }
}
