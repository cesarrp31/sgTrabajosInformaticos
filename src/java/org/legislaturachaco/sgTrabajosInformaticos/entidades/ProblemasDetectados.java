//
// This file was generated by the JPA Modeler
//
package org.legislaturachaco.sgTrabajosInformaticos.entidades;

import java.io.Serializable;
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
@Table(name = "problemasDetectados", uniqueConstraints = @UniqueConstraint(columnNames = {"idTrabajo", "idProblemaN3"}))

public class ProblemasDetectados implements Serializable {

    @ManyToOne(optional = false, targetEntity = ProblemasN3.class)
    @JoinColumn(name = "idProblemaN3")
    private ProblemasN3 idProblemaN3;

    @Column(name = "idProblemaDetectado", table = "problemasDetectados", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProblemaDetectado;

    @ManyToOne(optional = false, targetEntity = Trabajos.class)
    @JoinColumn(name = "idTrabajo")
    private Trabajos idTrabajo;

    public ProblemasDetectados() {

    }

    public ProblemasN3 getIdProblemaN3() {
        return this.idProblemaN3;
    }

    public void setIdProblemaN3(ProblemasN3 idProblemaN3) {
        this.idProblemaN3 = idProblemaN3;
    }

    public Integer getIdProblemaDetectado() {
        return this.idProblemaDetectado;
    }

    public void setIdProblemaDetectado(Integer idProblemaDetectado) {
        this.idProblemaDetectado = idProblemaDetectado;
    }

    public Trabajos getIdTrabajo() {
        return this.idTrabajo;
    }

    public void setIdTrabajo(Trabajos idTrabajo) {
        this.idTrabajo = idTrabajo;
    }
}
