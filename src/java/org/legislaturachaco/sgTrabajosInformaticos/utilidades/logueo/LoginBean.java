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
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.jdbcMySql.ConexionBaseDatos;
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
        /* 
        1° Ver si es un usuario local de la base de datos
            1°a: verificar si esta habilitado,
        2° Verificar si el usuario es de dominio y grupo,
            2°a: ver si no esta baneado
        3° Si no cumple 1° o 2° rechazar;        
         */

        logeado= false;
        if (!debug) {
            context= RequestContext.getCurrentInstance();
            msg= new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error",
                    "Credenciales no válidas");
        }
        
        //1º
        if (verificarUsuarioLocalBD()) {
            
        } else{//2
            if (verificarUsuarioDominio()) {

            } else {//3
                rechazarAccesoPagina();
            }
        }
        
        String paginaSiguiente= "";
        if (!debug) {
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.addCallbackParam("estaLogeado", logeado);

            if (logeado) {
                System.out.println("Usuario Logueado: "+usuarioLogueado.getNombreUsuario());
                context.addCallbackParam("view", PAGINA_SIGUIENTE);
                paginaSiguiente= PAGINA_SIGUIENTE;
            } else {
                context.addCallbackParam("view", INICIO2);
                paginaSiguiente= INICIO2;
            }
        }
        return paginaSiguiente;
    }

    private boolean verificarUsuarioLocalBD() {
        boolean resultado= false;
        ConexionBaseDatos cbd= new ConexionBaseDatos();
        try {
            usuarioLogueado= cbd.verificarUsuario(nombre, clave);
            resultado= true;
            logeado= true;
        } catch (UsuarioNoEncontradoException ex) {
            manejarErrores(ex, "Error de autenticación");
        } catch (ClaveUsuarioIncorrectaException ex) {
            manejarErrores(ex, "Credenciales inválidas");
        } catch (Exception ex) {
            manejarErrores(ex, "Se ha producido un error!");
        }
        return resultado;
    }

    private boolean verificarUsuarioDominio() {
        boolean resultado = false;
        ProcesoLogeoDominio pld = new ProcesoLogeoDominio();
        try {
            usuarioLogueado= pld.verificarUsuarioDominio(nombre, clave, grupo);
            resultado = true;
            logeado = true;
            System.out.println("Inicio sesión: " + nombre);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@ ", nombre);            
        } catch (AuthenticationException ae) {
            manejarErrores(ae, "Error de autenticación");
        } catch (UsuarioNoEncontradoException une) {
            manejarErrores(une, "Usuario no encontrado");
        }catch (UsuarioEnListaNegraException ulne) {
            manejarErrores(ulne, "Usuario baneado!!!");
        } catch (Exception ex) {
            manejarErrores(ex, ex.getLocalizedMessage());
        }

        return resultado;
    }
    
    private void rechazarAccesoPagina() {
        /*Exception e = new CredencialesNoValidasException();
        manejarErrores(e, e.getLocalizedMessage());*/
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

        System.out.println("Esta logeado?: " + lb.estaLogeado()+ " "+lb.getNombreCompleto());

        lb.logout();
    }
}
