/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.UsuariosController;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Usuarios;
import java.io.Serializable;
import java.util.Hashtable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
 
/**
 *
 * @author Cesar
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String nombre;
    private String clave;
    private boolean logeado = false;
    private static final String INICIO="/login.xhtml";
    
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
    public String login() {
        String usu= "admin";
        String sigPagina= INICIO;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
            /*
        if(consultaDominio()){
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", nombre);
            System.out.println("Inicio sesión: "+nombre);
            sigPagina= "/paginasAdmSist/indexAdmSist.xhtml?faces-redirect=true";
        }*/
        
        if (nombre != null && nombre.equals(usu) && 
                    clave != null && clave.equals("admin")) {
                logeado = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", nombre);
                System.out.println("Inicio sesión: "+nombre);
                sigPagina= "/paginasAdmSist/indexAdmSist.xhtml?faces-redirect=true";
        }else{
            logeado = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
            "Credenciales no válidas");
        }
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("estaLogeado", logeado);
        
        if (logeado)
            context.addCallbackParam("view", sigPagina);
        
        return sigPagina;
    } 
    
    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(false);
        session.invalidate();
        logeado= false;
        System.out.println("Fin sesión: "+nombre);
        
    }
    
    public String obtenerSalida(){
        return INICIO+"?faces-redirect=true";
    }
    
    public boolean consultaDominio(){
        final String LDAP_URL = "ldap://10.12.9.10:389/DC=legislaturachaco,DC=local";
        
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //env.put(Context.SECURITY_PRINCIPAL, "CN="+username.toUpperCase()+ ",OU=Dirección de Comunicaciones,OU=Usuarios,OU=Legislataura Chaco,DC=legislaturachaco,DC=local");
        env.put(Context.SECURITY_PRINCIPAL, nombre.toUpperCase()+ "@legislaturachaco.local");
        env.put(Context.SECURITY_CREDENTIALS, clave);
        
        try {
            DirContext ctx = new InitialDirContext(env);
            return true;
        } catch (NamingException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
}