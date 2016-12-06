/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap;

import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioNoPerteneceAGrupoExeption;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioNoEncontradoException;
import javax.naming.NamingException;

/**
 *
 * @author coperalta
 */
public class ProcesoLogeoDominio {
    private ConexionLDAP conexionLDAP;
    private UsuarioDominio usuario;

    public ProcesoLogeoDominio(){
        conexionLDAP= ConexionLDAP.getInstance();
    }
    
    public void verificarUsuarioDominio(String usuario, String clave) throws NamingException, UsuarioNoEncontradoException{
        conexionLDAP.crearConexion(usuario, clave);
        this.usuario = conexionLDAP.getUsuario(usuario);
        conexionLDAP.cerrarConexion();        
    }
    
    public void verificarGrupoDominio(String grupo) throws UsuarioNoPerteneceAGrupoExeption{
        if(!usuario.perteneceAGrupo(grupo)){
          throw new UsuarioNoPerteneceAGrupoExeption(usuario.getUserPrincipal(), grupo);
        }
    }
    
    public void verificarListaNegraDominio(){
        
    }
    
    public String getNombreCompleto(){
        return usuario.getCommonName();
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }
    
}
