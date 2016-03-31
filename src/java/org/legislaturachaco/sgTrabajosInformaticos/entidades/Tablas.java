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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tablas")

public class Tablas implements Serializable {

    @Column(name = "descripcion", table = "tablas", length = 200)
    @Basic
    private String descripcion;

    @OneToMany(targetEntity = MedicionXTabla.class, mappedBy = "tabla")
    private List<MedicionXTabla> medicionXTablaCollection;

    @Column(name = "tabla", table = "tablas", nullable = false, length = 100)
    @Id
    private String tabla;

    @OneToMany(targetEntity = Permisos.class, mappedBy = "tabla")
    private List<Permisos> permisosCollection;

    public Tablas() {

    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<MedicionXTabla> getMedicionXTablaCollection() {
        return this.medicionXTablaCollection;
    }

    public void setMedicionXTablaCollection(List<MedicionXTabla> medicionXTablaCollection) {
        this.medicionXTablaCollection = medicionXTablaCollection;
    }

    public String getTabla() {
        return this.tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public List<Permisos> getPermisosCollection() {
        return this.permisosCollection;
    }

    public void setPermisosCollection(List<Permisos> permisosCollection) {
        this.permisosCollection = permisosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.tabla);
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
        final Tablas other = (Tablas) obj;
        if (!Objects.equals(this.tabla, other.tabla)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tablas{" + "tabla=" + tabla + '}';
    }
    
}
