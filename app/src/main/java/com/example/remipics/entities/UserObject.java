package com.example.remipics.entities;

public class UserObject {
    private int usuario_id;
    private String correo;
    private String nombre;
    private String apellido;
    private String fecha_naci;
    private String sexo;
    private String direccion;

    public int getUsuario_id() {
        return usuario_id;
    }
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFecha_naci() {
        return fecha_naci;
    }

    public void setFecha_naci(String fecha_naci) {
        this.fecha_naci = fecha_naci;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public UserObject(int usuario_id, String correo, String nombre, String apellido, String fecha_naci, String sexo, String direccion) {
        this.usuario_id = usuario_id;
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_naci = fecha_naci;
        this.sexo = sexo;
        this.direccion = direccion;
    }

}
