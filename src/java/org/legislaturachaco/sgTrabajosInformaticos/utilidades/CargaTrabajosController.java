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

/**
 *
 * @author Cesar
 */
@Named(value = "cargaTrabajos")
@SessionScoped
@ViewScoped
public class CargaTrabajosController implements Serializable {

    //Creación del trabajo y sus 3 grandes tablas;
    private Trabajos trabajo;
    private Patrimonios patrimonioActual= null, patrimonioAgregadoActual= null;
    private Tecnicos tecnicoActual= null, tecnicoAgregadoActual= null;
    private ProblemasN3 problemaActual= null, problemaAgregadoActual= null;
    
    private ProblemasDetectados problemasDetectadoActual;
    private PatrimoniosXTrabajos patrimoniosXTrabajoActual;
    private EstadosXTrabajo estadosXTrabajoActual;
    
    //Creación del estadosXTrabajo y sus 4 grandes tablas;
    private EstadosTrabajo estadosTrabajoActual;
    private TareasRealizadas realizadasActual;
    private ElementosUtilizados elementosUtilizadosActual;
    private TecnicosXTrabajos tecnicosXTrabajo;
    
    private PatrimoniosController patrimoniosController;
    private TecnicosController tecnicosController;
    private ProblemasN3Controller problemasController;
    
    private TrabajosController trabajosController;
    private PatrimoniosXTrabajosController patrimoniosXTrabajosController;
    private ProblemasDetectadosController problemasDetectadosController;
    private EstadosXTrabajoController estadosXTrabajoController;
    
    private EstadosTrabajoController estadosTrabajoController;
    private TareasRealizadasController tareasRealizadasController;
    private ElementosUtilizadosController elementosUtilizadosController;
    private TecnicosXTrabajosController tecnicosXTrabajosController;
    
    private List<EstadosXTrabajo> estadosXTrabajos;
    
    private List<Tecnicos> tecnicosSeleccionados, tecnicosDisponibles;
    private List<Patrimonios> patrimoniosSeleccionados, patrimoniosDisponibles;
    private List<ProblemasN3> problemasSeleccionados, problemasDisponibles;
    
    @EJB
    private CargaTrabajosFacade ejbFacade;

    public CargaTrabajosController() {
    }
    
    /**
     * Devuelve una lista con todos los patrimonios que NO estad dados de baja y
     * todavía no estan asignados a una reparación (Trabajo).
     * @return 
     */
    public List<Patrimonios> listaPatrimoniosDisponibles(){
        return patrimoniosDisponibles;
    }
    
    public List<Tecnicos> listaTecnicosDisponibles(){
        return tecnicosDisponibles;
    }
    
    public List<ProblemasN3> listaProblemasDisponibles(){
        return problemasDisponibles;
    }
    
    public void listas(){
        System.out.println("Patrimonios: "+patrimoniosDisponibles.size()+" "+patrimoniosSeleccionados.size()+" "+patrimonioActual+" "+patrimonioAgregadoActual);
        System.out.println("Tecnicos: "+tecnicosDisponibles.size()+" "+tecnicosSeleccionados.size()+" "+tecnicoActual+" "+tecnicoAgregadoActual);
    }
    
    public void agregarPatrimonioSeleccionado(){
        boolean resultado= this.pasarObjeto(patrimonioActual, patrimoniosDisponibles, patrimoniosSeleccionados);
        if(resultado){
            patrimonioActual= null;
        }        
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
    
    public void removerPatrimonioSeleccionado(){
        boolean resultado= this.pasarObjeto(patrimonioAgregadoActual, patrimoniosSeleccionados, patrimoniosDisponibles);
        if(resultado){
            patrimonioAgregadoActual= null;
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

    public void inicializarListas(){
        this.inicializarListasPatrimonios();
        this.inicializarListasTecnicos();
        this.inicializarListasProblemas();
    }
    
    public void inicializarListasPatrimonios(){
        patrimoniosSeleccionados= new ArrayList();        
        patrimoniosDisponibles= patrimoniosController.obtenerPatrimoniosDisponibles();
    }
    
    public void inicializarListasTecnicos(){
        tecnicosSeleccionados= new ArrayList();
        tecnicosDisponibles= tecnicosController.obtenerTecnicosDisponibles();
    }
    
    public void inicializarListasProblemas(){
        problemasSeleccionados= new ArrayList();
        problemasDisponibles= problemasController.obtenerProblemasDisponibles();
    }

    public Trabajos prepareCreate() {
        trabajo= trabajosController.prepareCreate();
        return trabajo;
    }
    
    @PostConstruct
    public void inicializar(){
        trabajosController = (TrabajosController) Utiles.obtenerController("trabajosController");
        patrimoniosController= (PatrimoniosController) Utiles.obtenerController("patrimoniosController");
        tecnicosController= (TecnicosController) Utiles.obtenerController("tecnicosController");
        problemasController= (ProblemasN3Controller) Utiles.obtenerController("problemasN3Controller");
        
        inicializarListas();
    }
    
    public void create() {
        trabajosController.create();
        patrimoniosXTrabajosController.create();
        problemasDetectadosController.create();
        estadosXTrabajoController.create();
        
        estadosTrabajoController.create();
        tareasRealizadasController.create();
        elementosUtilizadosController.create();
        tecnicosXTrabajosController.create();
    }

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
    
}