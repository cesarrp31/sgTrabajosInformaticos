//
// This file was generated by the JPA Modeler
//
package org.legislaturachaco.sgTrabajosInformaticos.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dependencias")

public class Dependencias implements Serializable {
    
    public static final int LONG_MAX_OBSERVACIONES= 256, 
                            LONG_MAX_DEPENDENCIA= 75, 
                            LONG_MAX_NOMBRE_CORTO=25;

    @OneToMany(targetEntity = EntregaInsumos.class, mappedBy = "idDependencia")
    private List<EntregaInsumos> entregaInsumosCollection;

    @OneToMany(targetEntity = Asignaciones.class, mappedBy = "idDependencia")
    private List<Asignaciones> asignacionesCollection;

    @OneToMany(targetEntity = Notas.class, mappedBy = "idDependenciaDestino")
    private List<Notas> notasCollection1;

    @ManyToOne(optional = false, targetEntity = Encargados.class)
    @JoinColumn(name = "idEncargado")
    private Encargados idEncargado;

    @OneToMany(targetEntity = Notas.class, mappedBy = "idDependenciaOrigen")
    private List<Notas> notasCollection;

    @Column(name = "observaciones", table = "dependencias", length = 256)
    @Basic
    private String observaciones;

    @ManyToOne(optional = false, targetEntity = Dependencias.class)
    @JoinColumn(name = "dependeDe")
    private Dependencias dependeDe;

    @OneToMany(targetEntity = Dependencias.class, mappedBy = "dependeDe")
    private List<Dependencias> dependenciasCollection;

    @Column(name = "idDependencia", table = "dependencias", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDependencia;

    @Column(name = "dependencia", table = "dependencias", nullable = false, length = 75)
    @Basic
    private String dependencia;

    @OneToMany(targetEntity = Trabajos.class, mappedBy = "idDependencia")
    private List<Trabajos> trabajosCollection;

    @Column(name = "nombreCorto", table = "dependencias", length = 45)
    @Basic
    private String nombreCorto;

    public Dependencias() {

    }

    public List<EntregaInsumos> getEntregaInsumosCollection() {
        return this.entregaInsumosCollection;
    }

    public void setEntregaInsumosCollection(List<EntregaInsumos> entregaInsumosCollection) {
        this.entregaInsumosCollection = entregaInsumosCollection;
    }

    public List<Asignaciones> getAsignacionesCollection() {
        return this.asignacionesCollection;
    }

    public void setAsignacionesCollection(List<Asignaciones> asignacionesCollection) {
        this.asignacionesCollection = asignacionesCollection;
    }

    public List<Notas> getNotasCollection1() {
        return this.notasCollection1;
    }

    public void setNotasCollection1(List<Notas> notasCollection1) {
        this.notasCollection1 = notasCollection1;
    }

    public Encargados getIdEncargado() {
        return this.idEncargado;
    }

    public void setIdEncargado(Encargados idEncargado) {
        this.idEncargado = idEncargado;
    }

    public List<Notas> getNotasCollection() {
        return this.notasCollection;
    }

    public void setNotasCollection(List<Notas> notasCollection) {
        this.notasCollection = notasCollection;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Dependencias getDependeDe() {
        return this.dependeDe;
    }

    public void setDependeDe(Dependencias dependeDe) {
        this.dependeDe = dependeDe;
    }

    public List<Dependencias> getDependenciasCollection() {
        return this.dependenciasCollection;
    }

    public void setDependenciasCollection(List<Dependencias> dependenciasCollection) {
        this.dependenciasCollection = dependenciasCollection;
    }

    public Integer getIdDependencia() {
        return this.idDependencia;
    }

    public void setIdDependencia(Integer idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getDependencia() {
        return this.dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public List<Trabajos> getTrabajosCollection() {
        return this.trabajosCollection;
    }

    public void setTrabajosCollection(List<Trabajos> trabajosCollection) {
        this.trabajosCollection = trabajosCollection;
    }

    public String getNombreCorto() {
        return this.nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    @Override
    public String toString() {
        return "(" + "id:" + idDependencia + ", " + dependencia + ")";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.idDependencia);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dependencias other = (Dependencias) obj;
        if (!Objects.equals(this.idDependencia, other.idDependencia)) {
            return false;
        }
        return true;
    }
    
}
