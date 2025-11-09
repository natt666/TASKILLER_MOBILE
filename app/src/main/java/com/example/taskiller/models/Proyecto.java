package com.example.taskiller.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
public class Proyecto {
    public enum Estado {
        Por_Comenzar,
        En_Progreso,
        Entregado,
        Revisado,
        Bloqueado,
    }
    private UUID Id;
    private String Titulo;
    private String Descripcion;
    public String FechaInicio;
    public String FechaFinal;
    private Estado Estado;
    private List<UUID> listaUsuarios;
    public Proyecto() {

    }
    public Proyecto(String titulo, String descripcion, String fechaInicio, String fechaFinal,
                    Estado estado, List<UUID> usuarios) {
        this.Id = UUID.randomUUID();
        this.Titulo = titulo;
        this.Descripcion = descripcion;
        this.FechaInicio = fechaInicio;
        this.FechaFinal = fechaFinal;
        this.Estado = estado;
        this.listaUsuarios = usuarios;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        FechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        FechaFinal = fechaFinal;
    }

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado estado) {
        Estado = estado;
    }

    public List<UUID> getUsuarios() {
        return listaUsuarios;
    }

    public void setUsuarios(List<UUID> usuarios) {
        listaUsuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Proyecto proyecto = (Proyecto) o;
        return Objects.equals(Id, proyecto.Id) && Objects.equals(Titulo, proyecto.Titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, Titulo);
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "Id=" + Id +
                ", Titulo='" + Titulo + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", FechaInicio=" + FechaInicio +
                ", FechaFinal=" + FechaFinal +
                ", Estado=" + Estado +
                ", Usuarios=" + listaUsuarios +
                '}';
    }
}
