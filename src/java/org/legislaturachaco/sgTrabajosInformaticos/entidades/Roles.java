//
// This file was generated by the JPA Modeler
//
package org.legislaturachaco.sgTrabajosInformaticos.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")

public class Roles implements Serializable {

    @Column(name = "descripcion", table = "roles", length = 200)
    @Basic
    private String descripcion;

    @Column(name = "idRol", table = "roles", nullable = false, length = 15)
    @Id
    private String idRol;

    @OneToMany(targetEntity = Usuarios.class, mappedBy = "idRol")
    private List<Usuarios> usuariosCollection;

    @OneToMany(targetEntity = Permisos.class, mappedBy = "idRol")
    private List<Permisos> permisosCollection;

    @Column(name = "rol", table = "roles", nullable = false, length = 50)
    @Basic
    private String rol;

    public Roles() {

    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdRol() {
        return this.idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public List<Usuarios> getUsuariosCollection() {
        return this.usuariosCollection;
    }

    public void setUsuariosCollection(List<Usuarios> usuariosCollection) {
        this.usuariosCollection = usuariosCollection;
    }

    public List<Permisos> getPermisosCollection() {
        return this.permisosCollection;
    }

    public void setPermisosCollection(List<Permisos> permisosCollection) {
        this.permisosCollection = permisosCollection;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
