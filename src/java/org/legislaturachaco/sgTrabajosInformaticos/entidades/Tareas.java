//
// This file was generated by the JPA Modeler
//
package org.legislaturachaco.sgTrabajosInformaticos.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tareas")

public class Tareas implements Serializable {

    @Column(name = "tarea", table = "tareas", nullable = false, length = 100)
    @Basic
    private String tarea;

    @Column(name = "idTarea", table = "tareas", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTarea;

    @OneToMany(targetEntity = TareasXProblemas.class, mappedBy = "idTarea")
    private List<TareasXProblemas> tareasXProblemasCollection;

    @OneToMany(targetEntity = TareasRealizadas.class, mappedBy = "idTarea")
    private List<TareasRealizadas> tareasRealizadasCollection;

    public Tareas() {

    }

    public String getTarea() {
        return this.tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public Integer getIdTarea() {
        return this.idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public List<TareasXProblemas> getTareasXProblemasCollection() {
        return this.tareasXProblemasCollection;
    }

    public void setTareasXProblemasCollection(List<TareasXProblemas> tareasXProblemasCollection) {
        this.tareasXProblemasCollection = tareasXProblemasCollection;
    }

    public List<TareasRealizadas> getTareasRealizadasCollection() {
        return this.tareasRealizadasCollection;
    }

    public void setTareasRealizadasCollection(List<TareasRealizadas> tareasRealizadasCollection) {
        this.tareasRealizadasCollection = tareasRealizadasCollection;
    }
}
