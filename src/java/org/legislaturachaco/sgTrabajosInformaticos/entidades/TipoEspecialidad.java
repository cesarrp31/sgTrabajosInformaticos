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
@Table(name = "tipoEspecialidad")

public class TipoEspecialidad implements Serializable {

    @OneToMany(targetEntity = Tecnicos.class, mappedBy = "idTipoEspecialidad")
    private List<Tecnicos> tecnicosCollection;

    @Column(name = "especialidad", table = "tipoEspecialidad", nullable = false, length = 60)
    @Basic
    private String especialidad;

    @Column(name = "idTipoEspecialidad", table = "tipoEspecialidad", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoEspecialidad;

    public TipoEspecialidad() {

    }

    public List<Tecnicos> getTecnicosCollection() {
        return this.tecnicosCollection;
    }

    public void setTecnicosCollection(List<Tecnicos> tecnicosCollection) {
        this.tecnicosCollection = tecnicosCollection;
    }

    public String getEspecialidad() {
        return this.especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getIdTipoEspecialidad() {
        return this.idTipoEspecialidad;
    }

    public void setIdTipoEspecialidad(Integer idTipoEspecialidad) {
        this.idTipoEspecialidad = idTipoEspecialidad;
    }
}
