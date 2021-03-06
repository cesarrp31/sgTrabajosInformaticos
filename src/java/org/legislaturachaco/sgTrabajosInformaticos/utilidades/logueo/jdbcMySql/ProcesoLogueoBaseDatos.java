/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.jdbcMySql;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import org.legislaturachaco.sgTrabajosInformaticos.clasesJSF.UsuariosController;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.Usuarios;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.ClaveUsuarioIncorrectaException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.Utiles;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioNoEncontradoException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.UsuarioLogueado;

/**
 *
 * @author coperalta
 */
@Named(value = "conexionBaseDatos")
@SessionScoped
@ViewScoped
public class ProcesoLogueoBaseDatos implements Serializable {

    private final UsuariosController usuariosController;

    public ProcesoLogueoBaseDatos() {
        usuariosController = (UsuariosController) Utiles.obtenerController("usuariosController");
    }

    public UsuarioLogueado verificarUsuario(String nombre, String clave) 
            throws UsuarioNoEncontradoException, ClaveUsuarioIncorrectaException, NoSuchAlgorithmException {
        Usuarios usuario = usuariosController.getUsuarios(nombre);
        
        if (usuario == null) {
            throw new UsuarioNoEncontradoException();
        } else {
            if(usuario.getPassword().equals(Utiles.sha256(clave))){
                //usuario verificado ok
                //usuario.
                System.out.println("Usuario local válido!!!!!!!!!!!!!!!!!");
                return new UsuarioLogueado(usuario);
            }else{
                throw new ClaveUsuarioIncorrectaException();
            }
        }
    }
    
    public static void main(String[] args) 
            throws UsuarioNoEncontradoException, ClaveUsuarioIncorrectaException, NoSuchAlgorithmException {
        ProcesoLogueoBaseDatos c= new ProcesoLogueoBaseDatos();
        c.verificarUsuario("","");
    }
}
