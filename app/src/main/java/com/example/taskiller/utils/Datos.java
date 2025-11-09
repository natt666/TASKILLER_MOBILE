package com.example.taskiller.utils;

import com.example.taskiller.models.Proyecto;
import com.example.taskiller.models.Rol;
import com.example.taskiller.models.Tarea;
import com.example.taskiller.models.Usuario;

import java.util.List;

public class Datos{
    private List<Proyecto> listaProyectos;
    private List<Tarea> listaTareas;
    private List<Usuario> listaUsuarios;
    private List<Rol> listaRoles;


    public Datos(List<Proyecto> p, List<Tarea> t, List<Usuario> u, List<Rol> r) {
        this.listaProyectos = p;
        this.listaTareas = t;
        this.listaUsuarios = u;
        this.listaRoles = r;
    }

    public List<Proyecto> getListaProyectos() {
        return listaProyectos;
    }

    public void setListaProyectos(List<Proyecto> listaProyectos) {
        this.listaProyectos = listaProyectos;
    }

    public List<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void setListaTareas(List<Tarea> listaTareas) {
        this.listaTareas = listaTareas;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }
}
