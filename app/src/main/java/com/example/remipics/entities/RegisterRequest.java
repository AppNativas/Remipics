package com.example.remipics.entities;

public class RegisterRequest {

    private String correo;
    private String nombre;
    private String apellido;
    private String fecha_naci;
    private String sexo;
    private String contrasena;
    private String direccion;

    public RegisterRequest(String correo,
                           String nombre,
                           String apellido,
                           String fecha_naci,
                           String sexo,
                           String contrasena,
                           String direccion) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_naci = fecha_naci;
        this.sexo = sexo;
        this.contrasena = contrasena;
        this.direccion = direccion;
    }

}
