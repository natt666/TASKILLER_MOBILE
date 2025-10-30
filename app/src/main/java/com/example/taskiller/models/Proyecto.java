package com.example.taskiller.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
public class Proyecto {
    public enum Estado {
        POR_COMENZAR(1),
        EN_PROGRESO(2),
        ENTREGADO(3),
        REVISADO(4),
        BLOQUEADO(5);
        private final int valor;
        Estado(int valor) {
            this.valor = valor;
        }
        public int getValor() {
            return valor;
        }
    }
    private UUID id;
    private String titulo;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFinal;
    private Estado estado;
    private List<UUID> usuarios;
    public Proyecto() {

    }
    public Proyecto(String titulo, String descripcion, Date fechaInicio, Date fechaFinal,
                    Estado estado, List<UUID> usuarios) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.estado = estado;
        this.usuarios = usuarios;
    }

    public UUID getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public List<UUID> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(List<UUID> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Proyecto proyecto = (Proyecto) o;
        return Objects.equals(id, proyecto.id) && Objects.equals(titulo, proyecto.titulo);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, titulo);
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFinal=" + fechaFinal +
                ", estado=" + estado +
                ", usuarios=" + usuarios +
                '}';
    }
}
