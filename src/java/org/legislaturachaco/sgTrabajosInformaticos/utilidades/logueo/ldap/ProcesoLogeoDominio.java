/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap;

import java.util.ArrayList;
import java.util.List;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioNoPerteneceAGrupoExeption;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioNoEncontradoException;
import javax.naming.NamingException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioEnListaNegraException;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.UsuarioLogueado;

/**
 *
 * @author coperalta
 */
public class ProcesoLogeoDominio {
    private ConexionLDAP conexionLDAP;
    private UsuarioDominio usuario;
    private List<String> listaNegra= new ArrayList<>();

    public ProcesoLogeoDominio(){
        conexionLDAP= ConexionLDAP.getInstance();
        cargarListaNegra();
    }
    
    private void cargarListaNegra(){
        listaNegra.add("coperalta");
    }
    
    public UsuarioLogueado verificarUsuarioDominio(String nombreUsuario, String clave, String grupo) 
            throws NamingException, UsuarioNoEncontradoException, UsuarioNoPerteneceAGrupoExeption, UsuarioEnListaNegraException{
        conexionLDAP.crearConexion(nombreUsuario, clave);
        this.usuario = conexionLDAP.getUsuario(nombreUsuario);
        conexionLDAP.cerrarConexion();
        
        verificarGrupoDominio(grupo);
        verificarListaNegraDominio(nombreUsuario);
        
        return new UsuarioLogueado(usuario, null);
    }
    
    private void verificarGrupoDominio(String grupo) throws UsuarioNoPerteneceAGrupoExeption{
        if(!usuario.perteneceAGrupo(grupo)){
          throw new UsuarioNoPerteneceAGrupoExeption(usuario.getUserPrincipal(), grupo);
        }
    }
    
    private void verificarListaNegraDominio(String nombreUsuario) throws UsuarioEnListaNegraException{       
        for(String usuarioBaneado: listaNegra){
            if(usuarioBaneado.equals(nombreUsuario)){
                throw new UsuarioEnListaNegraException();
            }
        }        
    }
    
    public String getNombreCompleto(){
        return usuario.getCommonName();
    }

    public UsuarioDominio getUsuario() {
        return usuario;
    }
    
}
