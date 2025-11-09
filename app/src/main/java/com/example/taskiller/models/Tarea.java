package com.example.taskiller.models;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Tarea {
    public enum Prioridad {
        BAJA,
        MEDIA,
        ALTA;
    }
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
    private Prioridad Prioridad;
    public String FechaInicio;
    public String FechaFinal;
    private List<UUID> listaUsuarios;
    private UUID IdProyecto;
    private Estado Estado;
    private UUID IdTareaPadre;
    private List<Tarea> Subtareas;

    public Tarea() {
    }


    public UUID getId() {
        return Id;
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

    public Prioridad getPrioridad() {
        return Prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        Prioridad = prioridad;
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

    public List<UUID> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<UUID> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public UUID getIdProyecto() {
        return IdProyecto;
    }

    public void setIdProyecto(UUID idProyecto) {
        IdProyecto = idProyecto;
    }

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado estado) {
        Estado = estado;
    }

    public UUID getIdTareaPadre() {
        return IdTareaPadre;
    }

    public void setIdTareaPadre(UUID idTareaPadre) {
        IdTareaPadre = idTareaPadre;
    }

    public List<Tarea> getSubtareas() {
        return Subtareas;
    }

    public void setSubtareas(List<Tarea> subtareas) {
        Subtareas = subtareas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return Objects.equals(Id, tarea.Id) && Objects.equals(Titulo, tarea.Titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, Titulo);
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "Id=" + Id +
                ", Nombre='" + Titulo + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Prioridad=" + Prioridad +
                ", FechaInicio=" + FechaInicio +
                ", FechaFinal=" + FechaFinal +
                ", listaUsuarios=" + listaUsuarios +
                ", IdProyecto=" + IdProyecto +
                ", Estado=" + Estado +
                ", IdTareaPadre=" + IdTareaPadre +
                ", Subtareas=" + Subtareas +
                '}';
    }
}
