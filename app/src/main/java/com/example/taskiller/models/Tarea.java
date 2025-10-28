package com.example.taskiller.models;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Tarea {
    public enum Prioridad{ALTA, MEDIA, BAJA}

    private UUID id;
    private String nombre;
    private String descripcion;
    private Prioridad prioridad;
    private Date fechaInicio;
    private Date fechaFinal;
    private List<UUID> usuariosAsignados;
    private UUID idProyecto;
    private int estado; // 1: Por comenzar, 2: En progreso, 3: Entregado, 4: Revisado, 5: Bloqueado
    private UUID idTareaPadre;
    private List<Tarea> subtareas;

    public Tarea() {
    }

    public Tarea(String nombre, String descripcion, Prioridad prioridad, Date fechaInicio,
                 Date fechaFinal, List<UUID> usuariosAsignados, UUID idProyecto, int estado) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.usuariosAsignados = usuariosAsignados;
        this.idProyecto = idProyecto;
        this.estado = estado;
    }

    public Tarea(String nombre, String descripcion, Prioridad prioridad, Date fechaInicio,
                 Date fechaFinal, List<UUID> usuariosAsignados, UUID idProyecto, int estado,
                 UUID idTareaPadre, List<Tarea> subtareas) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.usuariosAsignados = usuariosAsignados;
        this.idProyecto = idProyecto;
        this.estado = estado;
        this.idTareaPadre = idTareaPadre;
        this.subtareas = subtareas;
    }

    public UUID getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Prioridad getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    public Date getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Date getFechaFinal() {
        return fechaFinal;
    }
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    public List<UUID> getUsuariosAsignados() {
        return usuariosAsignados;
    }
    public void setUsuariosAsignados(List<UUID> usuariosAsignados) {
        this.usuariosAsignados = usuariosAsignados;
    }
    public UUID getIdProyecto() {
        return idProyecto;
    }
    public void setIdProyecto(UUID idProyecto) {
        this.idProyecto = idProyecto;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
    public UUID getIdTareaPadre() {
        return idTareaPadre;
    }
    public void setIdTareaPadre(UUID idTareaPadre) {
        this.idTareaPadre = idTareaPadre;
    }
    public List<Tarea> getSubtareas() {
        return subtareas;
    }
    public void setSubtareas(List<Tarea> subtareas) {
        this.subtareas = subtareas;
    }

    @Override
    public String toString() {
        String usuarios = (usuariosAsignados != null && ! usuariosAsignados.isEmpty())
                ? usuariosAsignados.toString()
                : "Ninguno";
        return "Tarea " + id +
                " (Proyecto " + idProyecto + "): " + nombre + ", " + descripcion +
                ", " + fechaInicio + " - " + fechaFinal +
                ", Estado: " + estado +
                ", Prioridad: " + prioridad +
                ", Usuarios: [" + usuarios + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tarea tarea = (Tarea) o;
        return Objects.equals(id, tarea.id) && Objects.equals(nombre, tarea.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
