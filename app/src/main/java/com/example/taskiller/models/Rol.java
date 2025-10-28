package com.example.taskiller.models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Rol {

    private UUID id;
    private String nombre;
    private List<String> aceso;

    public Rol() { }
    public Rol(String nombre, List<String> acceso) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.aceso = acceso;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<String> getAceso() {
        return aceso;
    }
    public void setAceso(List<String> aceso) {
        this.aceso = aceso;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return Objects.equals(id, rol.id);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", aceso=" + aceso +
                '}';
    }
}
