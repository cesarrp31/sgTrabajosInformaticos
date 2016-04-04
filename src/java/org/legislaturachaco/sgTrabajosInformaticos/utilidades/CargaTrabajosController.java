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
import javax.inject.Named;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.EstadosTrabajoController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.EstadosXTrabajoController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.PatrimoniosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.PatrimoniosXTrabajosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.ProblemasN3Controller;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.TecnicosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.TecnicosXTrabajosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.TrabajosController;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Patrimonios;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Tecnicos;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Trabajos;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author coperalta
 */

@Named(value = "cargaTrabajos")
@SessionScoped
@ViewScoped
public class CargaTrabajosController implements Serializable{
    
    private Trabajos trabajo;
    //Lista dual Patrimonios
    private List<Patrimonios> patrimoniosSeleccionados, patrimoniosDisponibles;
    private DualListModel<Patrimonios> listaDualPatrimonios;
    
    private List<Tecnicos> tecnicosSeleccionados, tecnicosDisponibles;
    private DualListModel<Tecnicos> listaDualTecnicos;
    
    private TrabajosController trabajosController;
    
    private PatrimoniosController patrimoniosController;
    private PatrimoniosXTrabajosController patrimoniosXTrabajosController;
    
    private TecnicosController tecnicosController;
    private TecnicosXTrabajosController tecnicosXTrabajosController;
    
    @PostConstruct
    public void inicializar(){
        trabajosController = (TrabajosController) Utiles.obtenerController("trabajosController");
        
        patrimoniosController= (PatrimoniosController) Utiles.obtenerController("patrimoniosController");
        patrimoniosXTrabajosController= (PatrimoniosXTrabajosController) Utiles.obtenerController("patrimoniosXTrabajosController");
        tecnicosController= (TecnicosController) Utiles.obtenerController("tecnicosController");
        tecnicosXTrabajosController= (TecnicosXTrabajosController) Utiles.obtenerController("tecnicosXTrabajosController");
        /*
        
        problemasController= (ProblemasN3Controller) Utiles.obtenerController("problemasN3Controller");
        estadosXTrabajoController= (EstadosXTrabajoController) Utiles.obtenerController("estadosXTrabajoController");
        estadosTrabajoController= (EstadosTrabajoController) Utiles.obtenerController("estadosTrabajoController");
        */
        inicializarListas();
    }
    
    public void inicializarListas(){
        this.inicializarListasPatrimonios();
        this.inicializarListasTecnicos();
        this.inicializarListasProblemas();
    }
    
    /**
     * Devuelve una lista con todos los patrimonios que NO estad dados de baja y
     * todavía no estan asignados a una reparación (Trabajo).
     */
    public void inicializarListasPatrimonios(){
        patrimoniosSeleccionados= new ArrayList();        
        patrimoniosDisponibles= patrimoniosController.obtenerPatrimoniosDisponibles();
        
        listaDualPatrimonios= new DualListModel(patrimoniosDisponibles, patrimoniosSeleccionados);
    }
    
    public void inicializarListasTecnicos(){
        tecnicosSeleccionados= new ArrayList();
        tecnicosDisponibles= tecnicosController.obtenerTecnicosDisponibles();
        
        listaDualTecnicos= new DualListModel(tecnicosDisponibles, tecnicosSeleccionados);
    }
    
    public void inicializarListasProblemas(){
        /*
        problemasSeleccionados= new ArrayList();
        problemasDisponibles= problemasController.obtenerProblemasDisponibles();
        */
    }
    
    public Trabajos prepareCreate() {
        trabajo= trabajosController.prepareCreate();
        return trabajo;
    }
    
    public void create(){
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="Controles PickList Patrimonios">
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
    // </editor-fold> 
    
    // <editor-fold defaultstate="collapsed" desc="Getters y Setters Varios">
    public Trabajos getTrabajo() {
        return trabajo;
    }

    public void setTrabajo(Trabajos trabajo) {
        this.trabajo = trabajo;
    }

    public DualListModel<Patrimonios> getListaDualPatrimonios() {
        return listaDualPatrimonios;
    }

    public void setListaDualPatrimonios(DualListModel<Patrimonios> listaDualPatrimonios) {
        this.listaDualPatrimonios = listaDualPatrimonios;
    }

    public DualListModel<Tecnicos> getListaDualTecnicos() {
        return listaDualTecnicos;
    }

    public void setListaDualTecnicos(DualListModel<Tecnicos> listaDualTecnicos) {
        this.listaDualTecnicos = listaDualTecnicos;
    }
    
    // </editor-fold>
}
