/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
 
/**
 *
 * @author Cesar
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String nombre, nombreCompleto;
    private String clave;
    private boolean logeado = false;
    public static final String INICIO="login.xhtml";
    private static final String PAGINA_SIGUIENTE= "/paginasAdmSist/indexAdmSist.xhtml?faces-redirect=true",
            INICIO2="/"+INICIO;
    
    public boolean estaLogeado() {
        return logeado;
    } 
    public String getNombre() {
        return nombre;
    } 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    } 
    public String getClave() {
        return clave; 
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String login() {
        String grupo= "Soportecnicos";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;

        logeado = false;
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
            "Credenciales no válidas");
        try{
            Usuario u = ConexionLDap.getUser(nombre, clave);
            if(u.perteneceAGrupo(grupo)){
                nombreCompleto= u.getCommonName();
                logeado = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ ", nombre);
                    System.out.println("Inicio sesión: "+nombre);
            }else{
                logeado = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    "No pertenece a grupo");
                //throw new RuntimeException(nombre+" no pertenece a grupo"); 
            }
            
        } catch (AuthenticationException ex) {
            manejarErrores(ex, "Error de autenticación");
        } catch (Exception ex) {
            manejarErrores(ex, ex.getLocalizedMessage());
        }
             
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("estaLogeado", logeado);
        String sig;
        if (logeado){
            context.addCallbackParam("view", PAGINA_SIGUIENTE);
            sig= PAGINA_SIGUIENTE;
        }else{
            context.addCallbackParam("view", INICIO2);
            sig= INICIO2;
        }
        return sig;
    }
    
    private void manejarErrores(Exception ex, String msg){
        System.err.println(msg);
        ex.printStackTrace();
        logeado = false;
    }
    
    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(false);
        session.invalidate();
        logeado= false;
        System.out.println("Fin sesión: "+nombre);
        return INICIO2+"?faces-redirect=true";
    }
 
}