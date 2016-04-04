/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.ElementosUtilizadosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.EstadosTrabajoController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.EstadosXTrabajoController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.PatrimoniosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.PatrimoniosXTrabajosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.ProblemasDetectadosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.ProblemasN3Controller;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.TareasRealizadasController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.TecnicosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.TecnicosXTrabajosController;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.TrabajosController;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.ElementosUtilizados;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.EstadosTrabajo;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.EstadosXTrabajo;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Patrimonios;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.PatrimoniosXTrabajos;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.ProblemasDetectados;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.ProblemasN3;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.TareasRealizadas;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Tecnicos;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.TecnicosXTrabajos;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Trabajos;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Cesar
 */
@Named(value = "cargaTrabajosBkp")
@SessionScoped
@ViewScoped
public class CargaTrabajosControllerBkp implements Serializable {

    //Creación del trabajo y sus 3 grandes tablas;
    private Trabajos trabajo;
    private Patrimonios patrimonioActual= null, patrimonioAgregadoActual= null;
    private Tecnicos tecnicoActual= null, tecnicoAgregadoActual= null;
    private ProblemasN3 problemaActual= null, problemaAgregadoActual= null;
    
    /*
    private ProblemasDetectados problemasDetectadoActual;
    private PatrimoniosXTrabajos patrimoniosXTrabajoActual;
    
    //Creación del estadosXTrabajo y sus 4 grandes tablas;
    private TareasRealizadas realizadasActual;
    private ElementosUtilizados elementosUtilizadosActual;
    private TecnicosXTrabajos tecnicosXTrabajo;
     private ProblemasDetectadosController problemasDetectadosController;
    private TareasRealizadasController tareasRealizadasController;
    private ElementosUtilizadosController elementosUtilizadosController;
    private List<EstadosXTrabajo> estadosXTrabajos;
    */
    
    private PatrimoniosController patrimoniosController;
    private TecnicosController tecnicosController;
    private ProblemasN3Controller problemasController;
    private EstadosTrabajoController estadosTrabajoController;
    
    private EstadosXTrabajo estadosXTrabajo;
  
    private TrabajosController trabajosController;
    private PatrimoniosXTrabajosController patrimoniosXTrabajosController;
   
    private EstadosXTrabajoController estadosXTrabajoController;
    
    private TecnicosXTrabajosController tecnicosXTrabajosController;
    
    private List<Tecnicos> tecnicosSeleccionados, tecnicosDisponibles;
    private List<ProblemasN3> problemasSeleccionados, problemasDisponibles;

    //Lista dual Patrimonios
    private List<Patrimonios> patrimoniosSeleccionados, patrimoniosDisponibles;
    private DualListModel<Patrimonios> listaDualPatrimonios;

    public CargaTrabajosControllerBkp() {
    }
    
    public void listas(){
        System.out.println("Patrimonios: "+patrimoniosDisponibles.size()+" "+patrimoniosSeleccionados.size()+" "+patrimonioActual+" "+patrimonioAgregadoActual);
        System.out.println("Tecnicos: "+tecnicosDisponibles.size()+" "+tecnicosSeleccionados.size()+" "+tecnicoActual+" "+tecnicoAgregadoActual);
    }
    
    public void agregarTecnicoSeleccionado(){
        boolean resultado= this.pasarObjeto(tecnicoActual, tecnicosDisponibles, tecnicosSeleccionados);
        if(resultado){
            tecnicoActual= null;
        }
    }
    
    public void agregarProblemaSeleccionado(){
        boolean resultado= this.pasarObjeto(problemaActual, problemasDisponibles, problemasSeleccionados);
        if(resultado){
            problemaActual= null;
        }
    }
    
    public void removerTecnicoSeleccionado(){
        boolean resultado= this.pasarObjeto(tecnicoAgregadoActual, tecnicosSeleccionados, tecnicosDisponibles);
        if(resultado){
            tecnicoAgregadoActual= null;
        }   
    }
    
    public void removerProblemaSeleccionado(){
        boolean resultado= this.pasarObjeto(problemaAgregadoActual, problemasSeleccionados, problemasDisponibles);
        if(resultado){
            problemaAgregadoActual= null;
        }   
    }
    
    private boolean pasarObjeto(Object objetoAPasar, List listaOrigen, List listaDestino){
        if((objetoAPasar == null)||(listaOrigen.isEmpty())){
            System.out.println("Seleccion nulaaaaa");
            return false;
        }
        
        if(listaDestino.contains(objetoAPasar)){
            System.out.println("Nadaaaa");
            return false;
        }else{
            listaOrigen.remove(objetoAPasar);
            listaDestino.add(objetoAPasar);
            listas();
            return true;
        }
    }
    
    public Trabajos prepareCreate() {
        trabajo= trabajosController.prepareCreate();
        //inicializarListas();
        return trabajo;
    }
    
    public void create() {
        /*
        1)Crear trabajo
        2)Cargar los patrimonios en el trabajo (si existe)
        3a)Si no existe tecnicos, estado trabajo No Comenzado.
        3b)Cargar tecnicos (si existe). Estado trabajo Asignado
        4)Cargar problemas Detectados si existe.
        5)Gragar todo en la BD.
        */
        
        //Paso 1
        this.crearTrabajo();        
        //Paso 2
        this.cargarPatrimonios();              
        
        if(tecnicosSeleccionados.isEmpty()){
            //Paso 3a
            this.crearEstadoNoComenzado();
        }else{
            //Paso 3b
            this.crearEstadoAsignado();
        }        
        //Paso 5
        this.grabar();
    }
    
    private void crearTrabajo(){
        trabajosController.setSelected(trabajo);
        //trabajosController.create();
    }
    
    private void cargarPatrimonios(){
        PatrimoniosXTrabajos auxPat;
        for(Patrimonios p: patrimoniosSeleccionados){
            auxPat= patrimoniosXTrabajosController.prepareCreate();
            auxPat.setIdPatrimonio(p);
            auxPat.setIdTrabajo(trabajo);
            //patrimoniosXTrabajosController.setSelected(auxPat);
            //patrimoniosXTrabajosController.create();
            trabajo.getPatrimoniosXTrabajosCollection().add(auxPat);
        }
        
    }
    
    private void crearEstadoNoComenzado(){
        estadosXTrabajo= estadosXTrabajoController.prepareCreate();
        asignarEstadoTrabajo(Utiles.ESTADO_NO_COMENZADO, false);
    }
    
    private void crearEstadoAsignado(){
        TecnicosXTrabajos auxTec;
        estadosXTrabajo= estadosXTrabajoController.prepareCreate();
        asignarEstadoTrabajo(Utiles.ESTADO_ASIGNADO, false);
        for(Tecnicos t: tecnicosSeleccionados){
            auxTec= tecnicosXTrabajosController.prepareCreate();
            auxTec.setIdTecnico(t);
            auxTec.setIdEstadoXTrabajo(estadosXTrabajo);
            estadosXTrabajo.getTecnicosXTrabajosCollection().add(auxTec);
        }
        //estadosXTrabajo.setTecnicosXTrabajosCollection(auxTec);
    }
    
    private void asignarEstadoTrabajo(String et, boolean trabajoFirmado){
        trabajo.getEstadosXTrabajoCollection().add(estadosXTrabajo);
        estadosXTrabajo.setIdTrabajo(trabajo);
        estadosXTrabajo.setEstado(estadosTrabajoController.getEstadosTrabajo(et));
        estadosXTrabajo.setFechaDesde(trabajo.getFechaComienzo());
        estadosXTrabajo.setNroFicha(trabajo.getNroFicha());
        estadosXTrabajo.setTrabajoFirmado(trabajoFirmado);
    }
    
    private void grabar(){
        imprimirValoresTrabajoActual();
    }
    
    public void imprimirValoresTrabajoActual(){
        System.out.println("Trabajo");
        System.out.println(trabajo.toString());
        System.out.println("---------------------------");
        System.out.println("Patrimonios");
        for(PatrimoniosXTrabajos p: trabajo.getPatrimoniosXTrabajosCollection()){
            System.out.println(p.getIdPatrimonio());
        }
        
        System.out.println("---------------------------");
        System.out.println("Estados");
        for(EstadosXTrabajo e: trabajo.getEstadosXTrabajoCollection()){
            System.out.println(e.getEstado());
            System.out.println("---------------------------");
            System.out.println("TecnicosXTrabajos");
            for(TecnicosXTrabajos tt: e.getTecnicosXTrabajosCollection()){
                System.out.println(tt.getIdTecnico());
            }
        }
    }
    /*
    public void update() {    
        trabajosController.update();
        patrimoniosXTrabajosController.update();
        problemasDetectadosController.update();
        estadosTrabajoController.update();
        
        estadosXTrabajoController.update();
        tareasRealizadasController.update();
        elementosUtilizadosController.update();
        tecnicosXTrabajosController.update();
    }
    */
    public void destroy() {
        /*
        */
    }

    public List<Patrimonios> getPatrimoniosSeleccionados() {
        return patrimoniosSeleccionados;
    }
    
    public List<Tecnicos> getTecnicosSeleccionados() {
        return tecnicosSeleccionados;
    }

    public List<ProblemasN3> getProblemasSeleccionados() {
        return problemasSeleccionados;
    }
    
    public Trabajos getTrabajoActual() {
        return trabajo;
    }

    public void setTrabajoActual(Trabajos trabajo) {
        this.trabajo = trabajo;
    }

    public Patrimonios getPatrimonioActual() {
        return patrimonioActual;
    }

    public void setPatrimonioActual(Patrimonios patrimonioActual) {
        this.patrimonioActual = patrimonioActual;
    }

    public Patrimonios getPatrimonioAgregadoActual() {
        return patrimonioAgregadoActual;
    }

    public void setPatrimonioAgregadoActual(Patrimonios patrimonioAgregadoActual) {
        this.patrimonioAgregadoActual = patrimonioAgregadoActual;
    }

    public Tecnicos getTecnicoActual() {
        return tecnicoActual;
    }

    public void setTecnicoActual(Tecnicos tecnicoActual) {
        this.tecnicoActual = tecnicoActual;
    }

    public Tecnicos getTecnicoAgregadoActual() {
        return tecnicoAgregadoActual;
    }

    public void setTecnicoAgregadoActual(Tecnicos tecnicoAgregadoActual) {
        this.tecnicoAgregadoActual = tecnicoAgregadoActual;
    }

    public ProblemasN3 getProblemaActual() {
        return problemaActual;
    }

    public void setProblemaActual(ProblemasN3 problemaActual) {
        this.problemaActual = problemaActual;
    }

    public ProblemasN3 getProblemaAgregadoActual() {
        return problemaAgregadoActual;
    }

    public void setProblemaAgregadoActual(ProblemasN3 problemaAgregadoActual) {
        this.problemaAgregadoActual = problemaAgregadoActual;
    }

    public DualListModel<Patrimonios> getListaDualPatrimonios() {
        return listaDualPatrimonios;
    }

    public void setListaDualPatrimonios(DualListModel<Patrimonios> listaDualPatrimonios) {
        this.listaDualPatrimonios = listaDualPatrimonios;
    }
    
    public List<Tecnicos> listaTecnicosDisponibles(){
        return tecnicosDisponibles;
    }
    
    public List<ProblemasN3> listaProblemasDisponibles(){
        return problemasDisponibles;
    }
        
}