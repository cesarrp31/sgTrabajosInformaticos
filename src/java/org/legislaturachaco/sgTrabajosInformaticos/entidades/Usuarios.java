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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")

public class Usuarios implements Serializable {

    @OneToMany(targetEntity = EntregaInsumos.class, mappedBy = "usuarioIniciador")
    private List<EntregaInsumos> entregaInsumosCollection;

    @Column(name = "password", table = "usuarios", nullable = false, length = 64)
    @Basic
    private String password;

    @ManyToOne(optional = false, targetEntity = Roles.class)
    @JoinColumn(name = "idRol")
    private Roles idRol;

    @Column(name = "dominio", table = "usuarios", length = 45)
    @Basic
    private String dominio;

    @Column(name = "grupo", table = "usuarios", length = 45)
    @Basic
    private String grupo;

    @Column(name = "baja", table = "usuarios", nullable = false)
    @Basic
    private boolean baja;
    
    @Column(name = "usarClaveBD", table = "usuarios", nullable = false)
    @Basic
    private boolean usarClaveBD;

    @Column(name = "usuario", table = "usuarios", nullable = false, length = 45)
    @Id
    private String usuario;

    @ManyToOne(targetEntity = Tecnicos.class)
    @JoinColumn(name = "idTecnico")
    private Tecnicos idTecnico;

    @OneToMany(targetEntity = Trabajos.class, mappedBy = "usuarioIniciador")
    private List<Trabajos> trabajosCollection;

    public Usuarios() {

    }

    public List<EntregaInsumos> getEntregaInsumosCollection() {
        return this.entregaInsumosCollection;
    }

    public void setEntregaInsumosCollection(List<EntregaInsumos> entregaInsumosCollection) {
        this.entregaInsumosCollection = entregaInsumosCollection;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getIdRol() {
        return this.idRol;
    }

    public void setIdRol(Roles idRol) {
        this.idRol = idRol;
    }

    public String getDominio() {
        return this.dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getGrupo() {
        return this.grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public boolean isBaja() {
        return this.baja;
    }

    public void setBaja(boolean baja) {
        this.baja = baja;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Tecnicos getIdTecnico() {
        return this.idTecnico;
    }

    public void setIdTecnico(Tecnicos idTecnico) {
        this.idTecnico = idTecnico;
    }

    public List<Trabajos> getTrabajosCollection() {
        return this.trabajosCollection;
    }

    public void setTrabajosCollection(List<Trabajos> trabajosCollection) {
        this.trabajosCollection = trabajosCollection;
    }

    public boolean isUsarClaveBD() {
        return usarClaveBD;
    }

    public void setUsarClaveBD(boolean usarClaveBD) {
        this.usarClaveBD = usarClaveBD;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.usuario);
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
        final Usuarios other = (Usuarios) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return usuario;
    }
    
}
