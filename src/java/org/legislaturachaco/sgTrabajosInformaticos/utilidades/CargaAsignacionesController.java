/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.AsignacionesController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.PatrimoniosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.PatrimoniosXAsignacionesController;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Asignaciones;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Patrimonios;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.PatrimoniosXAsignaciones;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Cesar
 */
@Named(value = "cargaAsignaciones")
@SessionScoped
@ViewScoped
public class CargaAsignacionesController implements Serializable{
    
    private Asignaciones asignacion;
    private AsignacionesController asignacionesController;
    
    private PatrimoniosController patrimoniosController;
    private List<Patrimonios> patrimoniosSeleccionados, patrimoniosDisponibles;
    private DualListModel<Patrimonios> listaDualPatrimonios;
    
    private PatrimoniosXAsignacionesController patrimoniosXAsignacionesController;
    private PatrimoniosXAsignaciones patrimoniosXAsignaciones;
    
    public void prepareCreate(){
        asignacion= asignacionesController.prepareCreate();
    }
    
    public void crearAsignacionProvisoria(){
        if(asignacion == null) asignacionesController.prepareCreate();
    }
    
    @PostConstruct
    public void inicializar(){
        asignacionesController = (AsignacionesController) Utiles.obtenerController("asignacionesController");
        patrimoniosController= (PatrimoniosController) Utiles.obtenerController("patrimoniosController");
        patrimoniosXAsignacionesController= (PatrimoniosXAsignacionesController) Utiles.obtenerController("patrimoniosXAsignacionesController");
        inicializarListas();
    }
    
    private void inicializarListas() {
        patrimoniosDisponibles= patrimoniosController.obtenerPatrimoniosDisponiblesNoAsignados();
        patrimoniosSeleccionados= new ArrayList();
        listaDualPatrimonios= 
            new DualListModel(patrimoniosDisponibles, patrimoniosSeleccionados);
    }
    
    public DualListModel<Patrimonios> getListaDualPatrimonios(){
        return listaDualPatrimonios;
    }
    
    public void setListaDualPatrimonios(DualListModel<Patrimonios> listaDualPatrimonios){
        this.listaDualPatrimonios= listaDualPatrimonios;
    }
    
    
    public void create(){
        asignacion.setPatrimoniosXAsignacionesCollection(new ArrayList());
        for(Patrimonios p: listaDualPatrimonios.getTarget()){
            patrimoniosXAsignaciones= patrimoniosXAsignacionesController.prepareCreate();
            patrimoniosXAsignaciones.setIdAsignacion(asignacion);
            patrimoniosXAsignaciones.setFechaDesde(asignacion.getFechaDesde());
            patrimoniosXAsignaciones.setIdPatrimonio(p);
            patrimoniosXAsignaciones.setPrestado(false);
            p.setAsignado(Boolean.TRUE);
            //patrimoniosXAsignacionesController.create();
            asignacion.getPatrimoniosXAsignacionesCollection().add(patrimoniosXAsignaciones);
        }
        asignacionesController.create();
    }
    
    public void cancelar(){
        if(listaDualPatrimonios.getTarget().isEmpty()) return;
        for(Patrimonios p: listaDualPatrimonios.getTarget()){
            listaDualPatrimonios.getSource().add(p);
        }
        Collections.sort(listaDualPatrimonios.getSource());
    }
    
    public void onTransfer(){
        
    }
    
    public void onSelect(SelectEvent event) {
        Utiles.mensajesFacesContext(FacesMessage.SEVERITY_INFO, "Patrimonio Seleccionado", event.getObject().toString());
    }
    
    public void onUnselect(UnselectEvent event) {
        Utiles.mensajesFacesContext(FacesMessage.SEVERITY_INFO, "Patrimonio no seleccionado", event.getObject().toString());
    }
    
    public void onReorder(){
        Utiles.mensajesFacesContext(FacesMessage.SEVERITY_INFO, "Lista reordenada", null);
    }

    public Asignaciones getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignaciones asignacion) {
        this.asignacion = asignacion;
    }   

}
