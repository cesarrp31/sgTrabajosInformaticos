//
// This file was generated by the JPA Modeler
//
package org.legislaturachaco.sgTrabajosInformaticos.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ponderaciones")

public class Ponderaciones implements Serializable {
    public static final int LONG_MAX_PONDERACION= 45,
                            LONG_MAX_OBSERVACION= 100;

    @OneToMany(targetEntity = Patrimonios.class, mappedBy = "idPonderacion")
    private List<Patrimonios> patrimoniosCollection;

    @Column(name = "ponderacion", table = "ponderaciones", nullable = false, length = 45)
    @Basic
    private String ponderacion;

    @Column(name = "fechaHasta", table = "ponderaciones")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaHasta;

    @Column(name = "fechaDesde", table = "ponderaciones", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaDesde;

    @Column(name = "idPonderacion", table = "ponderaciones", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPonderacion;

    @OneToMany(targetEntity = CaracteristicasXPonderaciones.class, mappedBy = "idPonderacion")
    private List<CaracteristicasXPonderaciones> caracteristicasXPonderacionesCollection;

    @Column(name = "observacion", table = "ponderaciones", length = 100)
    @Basic
    private String observacion;

    public Ponderaciones() {

    }

    public List<Patrimonios> getPatrimoniosCollection() {
        return this.patrimoniosCollection;
    }

    public void setPatrimoniosCollection(List<Patrimonios> patrimoniosCollection) {
        this.patrimoniosCollection = patrimoniosCollection;
    }

    public String getPonderacion() {
        return this.ponderacion;
    }

    public void setPonderacion(String ponderacion) {
        this.ponderacion = ponderacion;
    }

    public Date getFechaHasta() {
        return this.fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Date getFechaDesde() {
        return this.fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Integer getIdPonderacion() {
        return this.idPonderacion;
    }

    public void setIdPonderacion(Integer idPonderacion) {
        this.idPonderacion = idPonderacion;
    }

    public List<CaracteristicasXPonderaciones> getCaracteristicasXPonderacionesCollection() {
        return this.caracteristicasXPonderacionesCollection;
    }

    public void setCaracteristicasXPonderacionesCollection(List<CaracteristicasXPonderaciones> caracteristicasXPonderacionesCollection) {
        this.caracteristicasXPonderacionesCollection = caracteristicasXPonderacionesCollection;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.idPonderacion);
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
        final Ponderaciones other = (Ponderaciones) obj;
        if (!Objects.equals(this.idPonderacion, other.idPonderacion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ponderaciones{" + "ponderacion=" + ponderacion + '}';
    }
    
}
