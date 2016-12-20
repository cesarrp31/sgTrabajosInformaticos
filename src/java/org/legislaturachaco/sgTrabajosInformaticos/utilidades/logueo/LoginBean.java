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
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.ClaveUsuarioIncorrectaException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioEnListaNegraException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap.ProcesoLogeoDominio;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioNoEncontradoException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.jdbcMySql.ProcesoLogueoBaseDatos;
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
    private boolean logeado = false, debug = false;
    private UsuarioLogueado usuarioLogueado;

    private final String grupo = "trabajosinformaticos";
    public static final String INICIO = "login.xhtml";
    private static final String PAGINA_SIGUIENTE = "/paginasAdmSist/indexAdmSist.xhtml?faces-redirect=true",
            INICIO2 = "/" + INICIO;

    private String msgErrorConsola, msgErrorPantallaResumen, msgErrorPantallaDetalle;

    private FacesMessage msg;
    private RequestContext context;

    private LoginBean(boolean debug) {
        this.debug = debug;
    }

    public LoginBean() {
        this(false);
    }

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
        return usuarioLogueado.getNombreUsuario();
    }

    public String login() {
        String paginaSiguiente = "";
        
        logeado = false;
        if (!debug) {
            context = RequestContext.getCurrentInstance();
            //msg = new FacesMessage();
        }
        
        if (verificarValidezCredenciales()) {
            /* 
            1° Ver si es un usuario local de la base de datos
                1°a: verificar si esta habilitado,
            2° Verificar si el usuario es de dominio y grupo,
                2°a: ver si no esta baneado
            3° Si no cumple 1° o 2° rechazar;        
            */

            //1º
            verificarUsuarioLocalBD();
            //2°
            if (!logeado) {
                verificarUsuarioDominio();
            }

            if (!debug) {
                /*
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("estaLogeado", logeado);*/

                if (logeado) {
                    aceptarAccesoPagina();
                    System.out.println("Usuario Logueado: " + usuarioLogueado.getNombreUsuario());
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    context.addCallbackParam("view", PAGINA_SIGUIENTE);
                    paginaSiguiente = PAGINA_SIGUIENTE;
                } else {
                    rechazarAccesoPagina();
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    context.addCallbackParam("view", INICIO2);
                    paginaSiguiente = INICIO2;
                }
            }
            return paginaSiguiente;
        } else {
            msgErrorConsola= msgErrorPantallaResumen= "Valores de credenciales Inválidas";
            msgErrorPantallaDetalle= msgErrorConsola+". Vuelva a ingresar valores";
            mensajes(msgErrorConsola, msgErrorPantallaResumen, msgErrorPantallaDetalle);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("view", INICIO2);
            return INICIO2;
        }
    }

    private boolean verificarValidezCredenciales() {
        String nulo = "null";

        return (!(nombre.isEmpty() || clave.isEmpty()
                || (nombre == null) || (clave == null)
                || (nombre.equals(nulo)) || (clave.equals(nulo))));
    }

    private boolean verificarUsuarioLocalBD() {
        boolean resultado = false;
        ProcesoLogueoBaseDatos cbd = new ProcesoLogueoBaseDatos();
        try {
            usuarioLogueado = cbd.verificarUsuario(nombre, clave);
            resultado = true;
            logeado = true;
        } catch (UsuarioNoEncontradoException ex) {
            manejarErrores(ex, "Error de autenticación");
        } catch (ClaveUsuarioIncorrectaException ex) {
            manejarErrores(ex, "Credenciales inválidas");
        } catch (Exception ex) {
            manejarErrores(ex, ex.getLocalizedMessage());
        }
        return resultado;
    }

    private boolean verificarUsuarioDominio() {
        boolean resultado = false;
        ProcesoLogeoDominio pld = new ProcesoLogeoDominio();
        try {
            usuarioLogueado = pld.verificarUsuarioDominioValidoParaSistema(nombre, clave, grupo);
            resultado = true;
            logeado = true;
        } catch (AuthenticationException ae) {
            manejarErrores(ae, "Error de autenticación");
        } catch (UsuarioNoEncontradoException une) {
            manejarErrores(une, "Usuario no encontrado");
        } catch (UsuarioEnListaNegraException ulne) {
            manejarErrores(ulne, "Usuario baneado!!!");
        } catch (Exception ex) {
            manejarErrores(ex, ex.getLocalizedMessage());
        }
        return resultado;
    }

    private void aceptarAccesoPagina() {
        mensajes("Inicio sesión: " + nombre, "Bienvenid@ ", nombre);
    }

    private void rechazarAccesoPagina() {
        String s = "No se puede iniciar sesión con el usuario: ";
        //mensajes(s + nombre, s, nombre);
        mensajes(s + msgErrorConsola, msgErrorPantallaResumen, s + msgErrorPantallaDetalle);
    }

    private void manejarErrores(Exception ex, String comentario) {
        //mensajes(comentario, "Login Error", comentario);
        this.msgErrorConsola = comentario;
        this.msgErrorPantallaResumen = "Login Error";
        this.msgErrorPantallaDetalle = comentario + ". " + ex.getLocalizedMessage();
        ex.printStackTrace();
        logeado = false;
    }

    private void mensajes(String msgConsola, String msgPantallaResumen, String msgPantallaDetalle) {
        System.out.println(msgConsola);
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, msgPantallaResumen, msgPantallaDetalle);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String logout() {
        if (!debug) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.invalidate();
        }
        logeado = false;
        System.out.println("Fin sesión: " + nombre);
        return INICIO2 + "?faces-redirect=true";
    }

}
