//
// This file was generated by the JPA Modeler
//
package org.legislaturachaco.sgTrabajosInformaticos.entidades;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "patrimoniosXTrabajos", uniqueConstraints = @UniqueConstraint(columnNames = {"idTrabajo", "idPatrimonio"}))

public class PatrimoniosXTrabajos implements Serializable {

    @ManyToOne(optional = false, targetEntity = Patrimonios.class)
    @JoinColumn(name = "idPatrimonio")
    private Patrimonios idPatrimonio;

    @ManyToOne(optional = false, targetEntity = Trabajos.class)
    @JoinColumn(name = "idTrabajo")
    private Trabajos idTrabajo;

    @Column(name = "idPatrimonioXTrabajo", table = "patrimoniosXTrabajos", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPatrimonioXTrabajo;

    public PatrimoniosXTrabajos() {

    }

    public Patrimonios getIdPatrimonio() {
        return this.idPatrimonio;
    }

    public void setIdPatrimonio(Patrimonios idPatrimonio) {
        this.idPatrimonio = idPatrimonio;
    }

    public Trabajos getIdTrabajo() {
        return this.idTrabajo;
    }

    public void setIdTrabajo(Trabajos idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public Integer getIdPatrimonioXTrabajo() {
        return this.idPatrimonioXTrabajo;
    }

    public void setIdPatrimonioXTrabajo(Integer idPatrimonioXTrabajo) {
        this.idPatrimonioXTrabajo = idPatrimonioXTrabajo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.idPatrimonioXTrabajo);
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
        final PatrimoniosXTrabajos other = (PatrimoniosXTrabajos) obj;
        if (!Objects.equals(this.idPatrimonioXTrabajo, other.idPatrimonioXTrabajo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PatrimoniosXTrabajos{" + "idPatrimonio=" + idPatrimonio + ", idTrabajo=" + idTrabajo + '}';
    }
    
}
