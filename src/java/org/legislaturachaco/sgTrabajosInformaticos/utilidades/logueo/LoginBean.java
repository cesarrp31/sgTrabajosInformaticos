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
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap.CredencialesNoValidasException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap.ProcesoLogeoDominio;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap.Usuario;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap.UsuarioNoEncontradoException;
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
    private Usuario usuario;

    private final String grupo = "trabajosinformaticos";
    public static final String INICIO = "login.xhtml";
    private static final String PAGINA_SIGUIENTE = "/paginasAdmSist/indexAdmSist.xhtml?faces-redirect=true",
            INICIO2 = "/" + INICIO;

    private FacesMessage msg;

    private RequestContext context;

    private LoginBean(boolean debug) {
        this();
        this.debug = debug;
    }

    public LoginBean() {
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
        return usuario.getCommonName();
    }

    public String login() {
        /* 
        1° Ver si es un usuario local de la base de datos
            1°a: verificar si esta habilitado,
        2° Verificar si el usuario es de dominio y grupo,
            2°a: ver si no esta baneado
        3° Si no cumple 1° o 2° rechazar;        
         */

        if (!debug) {
            context = RequestContext.getCurrentInstance();
        }

        logeado = false;

        if (!debug) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    "Credenciales no válidas");
        }

        //1º
        if (verificarUsuarioLocalBD()) {

        } else//2
        if (verificarUsuarioDominio()) {
            
        } else {//3
            rechazarAccesoPagina();
        }
        String sig = "";
        if (!debug) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("estaLogeado", logeado);

            if (logeado) {
                System.out.println("Usuario Logueado: "+usuario.getCommonName());
                context.addCallbackParam("view", PAGINA_SIGUIENTE);
                sig = PAGINA_SIGUIENTE;
            } else {
                context.addCallbackParam("view", INICIO2);
                sig = INICIO2;
            }
        }
        return sig;
    }

    private boolean verificarUsuarioLocalBD() {
        boolean resultado = false;
        return resultado;
    }

    private boolean verificarUsuarioDominio() {
        boolean resultado = false;
        ProcesoLogeoDominio pld = new ProcesoLogeoDominio();
        try {
            pld.verificarUsuarioDominio(nombre, clave);
            pld.verificarGrupoDominio(grupo);
            pld.verificarListaNegraDominio();
            resultado = true;
            usuario= pld.getUsuario();
            logeado = true;
            System.out.println("Inicio sesión: " + nombre);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ ", nombre);
            /*
            if(u.perteneceAGrupo(grupo)){
                nombreCompleto= u.getCommonName();
                logeado = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ ", nombre);
            }else{
                logeado = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    "No pertenece a grupo");
                //throw new RuntimeException(nombre+" no pertenece a grupo"); 
            }            
            conexionLDAP.cerrarConexion();*/
        } catch (AuthenticationException ae) {
            manejarErrores(ae, "Error de autenticación");
        } catch (UsuarioNoEncontradoException une) {
            manejarErrores(une, "Usuario no encontrado");
        } catch (Exception ex) {
            manejarErrores(ex, ex.getLocalizedMessage());
        }

        return resultado;
    }

    private void rechazarAccesoPagina() {
        Exception e = new CredencialesNoValidasException();
        manejarErrores(e, e.getLocalizedMessage());
    }

    private void manejarErrores(Exception ex, String comentario) {
        System.err.println(comentario);
        msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                "Error " + comentario);
        ex.printStackTrace();
        logeado = false;
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

    public static void main(String[] args) {
        LoginBean lb = new LoginBean(true);
        lb.setNombre("accesotrabinf");
        lb.setClave("trabinfacceso2016");

        lb.login();

        System.out.println("Esta logeado?: " + lb.estaLogeado()+ " "+lb.usuario);

        lb.logout();
    }
}
