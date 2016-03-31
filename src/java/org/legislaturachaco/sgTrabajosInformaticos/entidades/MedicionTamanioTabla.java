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
@Table(name = "medicionTamanioTabla")

public class MedicionTamanioTabla implements Serializable {

    @Column(name = "fecha", table = "medicionTamanioTabla", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fecha;

    @OneToMany(targetEntity = MedicionXTabla.class, mappedBy = "idMedicion")
    private List<MedicionXTabla> medicionXTablaCollection;

    @Column(name = "idMedicion", table = "medicionTamanioTabla", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedicion;

    @Column(name = "observacion", table = "medicionTamanioTabla", length = 100)
    @Basic
    private String observacion;

    public MedicionTamanioTabla() {

    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<MedicionXTabla> getMedicionXTablaCollection() {
        return this.medicionXTablaCollection;
    }

    public void setMedicionXTablaCollection(List<MedicionXTabla> medicionXTablaCollection) {
        this.medicionXTablaCollection = medicionXTablaCollection;
    }

    public Integer getIdMedicion() {
        return this.idMedicion;
    }

    public void setIdMedicion(Integer idMedicion) {
        this.idMedicion = idMedicion;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idMedicion);
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
        final MedicionTamanioTabla other = (MedicionTamanioTabla) obj;
        if (!Objects.equals(this.idMedicion, other.idMedicion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MedicionTamanioTabla{" + "fecha=" + fecha + ", idMedicion=" + idMedicion + '}';
    }
    
}
