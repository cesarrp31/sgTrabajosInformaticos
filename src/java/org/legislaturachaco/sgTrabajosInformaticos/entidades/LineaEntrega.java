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
@Table(name = "lineaEntrega", uniqueConstraints = @UniqueConstraint(columnNames = {"idInsumo", "idEntregaInsumo"}))

public class LineaEntrega implements Serializable {

    @ManyToOne(optional = false, targetEntity = EntregaInsumos.class)
    @JoinColumn(name = "idEntregaInsumo")
    private EntregaInsumos idEntregaInsumo;

    @ManyToOne(optional = false, targetEntity = InsumosDisponibles.class)
    @JoinColumn(name = "idInsumo")
    private InsumosDisponibles idInsumo;

    @Column(name = "idLineaEntrega", table = "lineaEntrega", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLineaEntrega;

    @Column(name = "cantidad", table = "lineaEntrega", nullable = false)
    @Basic
    private double cantidad;

    public LineaEntrega() {

    }

    public EntregaInsumos getIdEntregaInsumo() {
        return this.idEntregaInsumo;
    }

    public void setIdEntregaInsumo(EntregaInsumos idEntregaInsumo) {
        this.idEntregaInsumo = idEntregaInsumo;
    }

    public InsumosDisponibles getIdInsumo() {
        return this.idInsumo;
    }

    public void setIdInsumo(InsumosDisponibles idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Integer getIdLineaEntrega() {
        return this.idLineaEntrega;
    }

    public void setIdLineaEntrega(Integer idLineaEntrega) {
        this.idLineaEntrega = idLineaEntrega;
    }

    public double getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.idLineaEntrega);
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
        final LineaEntrega other = (LineaEntrega) obj;
        if (!Objects.equals(this.idLineaEntrega, other.idLineaEntrega)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LineaEntrega{" + "idEntregaInsumo=" + idEntregaInsumo + ", idInsumo=" + idInsumo + ", cantidad=" + cantidad + '}';
    }
    
}
