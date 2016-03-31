//
// This file was generated by the JPA Modeler
//
package org.legislaturachaco.sgTrabajosInformaticos.entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "insumosXPatrimonio", uniqueConstraints = @UniqueConstraint(columnNames = {"idPatrimonio", "idInsumo"}))

public class InsumosXPatrimonio implements Serializable {

    @ManyToOne(optional = false, targetEntity = Patrimonios.class)
    @JoinColumn(name = "idPatrimonio")
    private Patrimonios idPatrimonio;

    @Column(name = "fechaHasta", table = "insumosXPatrimonio")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaHasta;

    @Column(name = "idInsumoXPatrimonio", table = "insumosXPatrimonio", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInsumoXPatrimonio;

    @ManyToOne(optional = false, targetEntity = InsumosDisponibles.class)
    @JoinColumn(name = "idInsumo")
    private InsumosDisponibles idInsumo;

    @Column(name = "fechaDesde", table = "insumosXPatrimonio", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaDesde;

    @Column(name = "cantidad", table = "insumosXPatrimonio", nullable = false)
    @Basic
    private double cantidad;

    public InsumosXPatrimonio() {

    }

    public Patrimonios getIdPatrimonio() {
        return this.idPatrimonio;
    }

    public void setIdPatrimonio(Patrimonios idPatrimonio) {
        this.idPatrimonio = idPatrimonio;
    }

    public Date getFechaHasta() {
        return this.fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getIdInsumoXPatrimonio() {
        return this.idInsumoXPatrimonio;
    }

    public void setIdInsumoXPatrimonio(Integer idInsumoXPatrimonio) {
        this.idInsumoXPatrimonio = idInsumoXPatrimonio;
    }

    public InsumosDisponibles getIdInsumo() {
        return this.idInsumo;
    }

    public void setIdInsumo(InsumosDisponibles idInsumo) {
        this.idInsumo = idInsumo;
    }

    public Date getFechaDesde() {
        return this.fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public double getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idInsumoXPatrimonio);
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
        final InsumosXPatrimonio other = (InsumosXPatrimonio) obj;
        if (!Objects.equals(this.idInsumoXPatrimonio, other.idInsumoXPatrimonio)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InsumosXPatrimonio{" + "idPatrimonio=" + idPatrimonio + ", idInsumo=" + idInsumo + ", fechaDesde=" + fechaDesde + ", cantidad=" + cantidad + '}';
    }
    
}
