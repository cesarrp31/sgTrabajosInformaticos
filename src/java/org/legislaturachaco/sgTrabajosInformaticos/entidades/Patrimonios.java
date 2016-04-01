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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "patrimonios")

public class Patrimonios implements Serializable, Comparable<Patrimonios> {
    public static int LONG_MAX_PATRIMONIO= 9,
                    LONG_MAX_COMENTARIO= 100;            

    @Column(name = "idPatrimonio", table = "patrimonios", nullable = false, length = 9)
    @Id
    private String idPatrimonio;

    @Column(name = "fechaBaja", table = "patrimonios")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaBaja;

    @Column(name = "fechaAlta", table = "patrimonios", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaAlta;

    @Column(name = "baja", table = "patrimonios")
    @Basic
    private Boolean baja;
    
    @Column(name = "enReparacion", table = "patrimonios", nullable = false)
    @Basic
    private Boolean enReparacion;
    
    @Column(name = "asignado", table = "patrimonios", nullable = false)
    @Basic
    private Boolean asignado;

    @OneToMany(targetEntity = PatrimoniosXTrabajos.class, mappedBy = "idPatrimonio")
    private List<PatrimoniosXTrabajos> patrimoniosXTrabajosCollection;

    @OneToMany(targetEntity = InsumosXPatrimonio.class, mappedBy = "idPatrimonio")
    private List<InsumosXPatrimonio> insumosXPatrimonioCollection;

    @ManyToOne(optional = false, targetEntity = TipoPatrimonio.class)
    @JoinColumn(name = "idTipo")
    private TipoPatrimonio idTipo;

    @ManyToOne(optional = false, targetEntity = Ponderaciones.class)
    @JoinColumn(name = "idPonderacion")
    private Ponderaciones idPonderacion;

    @OneToMany(targetEntity = PatrimoniosXAsignaciones.class, mappedBy = "idPatrimonio")
    private List<PatrimoniosXAsignaciones> patrimoniosXAsignacionesCollection;

    @Column(name = "comentario", table = "patrimonios", length = 100)
    @Basic
    private String comentario;

    public Patrimonios() {

    }

    public String getIdPatrimonio() {
        return this.idPatrimonio;
    }

    public void setIdPatrimonio(String idPatrimonio) {
        this.idPatrimonio = idPatrimonio;
    }

    public Date getFechaBaja() {
        return this.fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaAlta() {
        return this.fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Boolean isBaja() {
        return this.baja;
    }
    
    public Boolean getBaja() {
        return this.baja;
    }

    public void setBaja(Boolean baja) {
        this.baja = baja;
    }

    public List<PatrimoniosXTrabajos> getPatrimoniosXTrabajosCollection() {
        return this.patrimoniosXTrabajosCollection;
    }

    public void setPatrimoniosXTrabajosCollection(List<PatrimoniosXTrabajos> patrimoniosXTrabajosCollection) {
        this.patrimoniosXTrabajosCollection = patrimoniosXTrabajosCollection;
    }

    public List<InsumosXPatrimonio> getInsumosXPatrimonioCollection() {
        return this.insumosXPatrimonioCollection;
    }

    public void setInsumosXPatrimonioCollection(List<InsumosXPatrimonio> insumosXPatrimonioCollection) {
        this.insumosXPatrimonioCollection = insumosXPatrimonioCollection;
    }

    public TipoPatrimonio getIdTipo() {
        return this.idTipo;
    }

    public void setIdTipo(TipoPatrimonio idTipo) {
        this.idTipo = idTipo;
    }

    public Ponderaciones getIdPonderacion() {
        return this.idPonderacion;
    }

    public void setIdPonderacion(Ponderaciones idPonderacion) {
        this.idPonderacion = idPonderacion;
    }

    public List<PatrimoniosXAsignaciones> getPatrimoniosXAsignacionesCollection() {
        return this.patrimoniosXAsignacionesCollection;
    }

    public void setPatrimoniosXAsignacionesCollection(List<PatrimoniosXAsignaciones> patrimoniosXAsignacionesCollection) {
        this.patrimoniosXAsignacionesCollection = patrimoniosXAsignacionesCollection;
    }

    public String getComentario() {
        return this.comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Boolean getEnReparacion() {
        return enReparacion;
    }

    public void setEnReparacion(Boolean enReparacion) {
        this.enReparacion = enReparacion;
    }

    public Boolean getAsignado() {
        return asignado;
    }

    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idPatrimonio);
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
        final Patrimonios other = (Patrimonios) obj;
        if (!Objects.equals(this.idPatrimonio, other.idPatrimonio)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Patrimonios{" + "idPatrimonio=" + idPatrimonio + ", idPonderacion=" + idPonderacion + '}';
    }

    @Override
    public int compareTo(Patrimonios o) {
        if(o == null) return 1;
        int valor1= Integer.parseInt(this.idPatrimonio),
                valor2= Integer.parseInt(o.idPatrimonio);
        
        return valor1 - valor2;
    }
    
    public int getIdPatrimoniosInt(){
        if(this.idPatrimonio == null) return 0;
        return Integer.valueOf(this.idPatrimonio);
    }
}
