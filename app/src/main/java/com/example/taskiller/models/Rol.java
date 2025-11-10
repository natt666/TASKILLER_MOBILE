package com.example.taskiller.models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Rol {

    private UUID Id;
    private String Nombre;
    private List<String> Acceso;

    public Rol() { }

    public UUID getId() {
        return Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }




    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return Objects.equals(Id, rol.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Id);
    }

    @Override
    public String toString() {
        return "Rol{" +
                "Id=" + Id +
                ", Nombre='" + Nombre + '\'' +
                ", Aceso=" + Acceso +
                '}';
    }
}
