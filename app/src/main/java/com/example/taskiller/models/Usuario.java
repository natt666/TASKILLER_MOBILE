package com.example.taskiller.models;
import java.util.Objects;
import java.util.UUID;

public class Usuario {
    private UUID Id;
    private String Nombre;
    private String Apellido;
    private String Mail;
    private String Contrasena;
    private UUID Rol;

    public Usuario() {}

    public Usuario(UUID id, String nombre, String apellido, String mail, String contrasena, UUID rol) {
        Id = UUID.randomUUID();
        Nombre = nombre;
        Apellido = apellido;
        Mail = mail;
        Contrasena = contrasena;
        Rol = rol;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public UUID getRol() {
        return Rol;
    }

    public void setRol(UUID rol) {
        Rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Id=" + Id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellido='" + Apellido + '\'' +
                ", Mail='" + Mail + '\'' +
                ", Contrasena='" + Contrasena + '\'' +
                ", Rol=" + Rol +
                '}';
    }
}
