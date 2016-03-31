//
// This file was generated by the JPA Modeler
//
package org.legislaturachaco.sgTrabajosInformaticos.entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "transicionesEstado", uniqueConstraints = @UniqueConstraint(columnNames = {"estado", "estadoSiguiente"}))

public class TransicionesEstado implements Serializable {

    @Column(name = "descripcion", table = "transicionesEstado", length = 100)
    @Basic
    private String descripcion;

    @ManyToOne(optional = false, targetEntity = EstadosTrabajo.class)
    @JoinColumn(name = "estado")
    private EstadosTrabajo estado;

    @ManyToOne(optional = false, targetEntity = EstadosTrabajo.class)
    @JoinColumn(name = "estadoSiguiente")
    private EstadosTrabajo estadoSiguiente;

    @Column(name = "idProxEstado", table = "transicionesEstado", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProxEstado;

    public TransicionesEstado() {

    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadosTrabajo getEstado() {
        return this.estado;
    }

    public void setEstado(EstadosTrabajo estado) {
        this.estado = estado;
    }

    public EstadosTrabajo getEstadoSiguiente() {
        return this.estadoSiguiente;
    }

    public void setEstadoSiguiente(EstadosTrabajo estadoSiguiente) {
        this.estadoSiguiente = estadoSiguiente;
    }

    public Integer getIdProxEstado() {
        return this.idProxEstado;
    }

    public void setIdProxEstado(Integer idProxEstado) {
        this.idProxEstado = idProxEstado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idProxEstado);
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
        final TransicionesEstado other = (TransicionesEstado) obj;
        if (!Objects.equals(this.idProxEstado, other.idProxEstado)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransicionesEstado{" + "estado=" + estado + ", estadoSiguiente=" + estadoSiguiente + '}';
    }
    
}
