/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.AsignacionesController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.PatrimoniosController;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Asignaciones;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Patrimonios;
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
    private AsignacionesController asigCon;
    
    private Patrimonios patrimonioActual= null, patrimonioAgregadoActual= null;
    private List<Patrimonios> patrimoniosSeleccionados, patrimoniosDisponibles;
    private DualListModel<Patrimonios> listaDualPatrimonios;
    
    private PatrimoniosController patrimoniosController;
    
    public void prepareCreate(){
        asignacion= asigCon.prepareCreate();
    }
    
    @PostConstruct
    public void inicializar(){
        asigCon = (AsignacionesController) Utiles.obtenerController("asignacionesController");
        patrimoniosController= (PatrimoniosController) Utiles.obtenerController("patrimoniosController");
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
        
    }
    
    public void onTransfer(){
        
    }
    
    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Patrimonio Seleccionado", event.getObject().toString())); 
    }
    
    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Patrimonio no seleccionado", event.getObject().toString()));
    }
    
    public void onReorder(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Lista reordenada", null));
    }

    public Asignaciones getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignaciones asignacion) {
        this.asignacion = asignacion;
    }   

}
