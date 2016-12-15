/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.faces.context.FacesContext;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioNoPerteneceAGrupoExeption;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.UsuarioNoEncontradoException;
import javax.naming.NamingException;
import javax.naming.PartialResultException;
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
        String path= "/WEB-INF/configuraciones/listaNegraUsuarios";
        InputStream s= FacesContext.getCurrentInstance().getExternalContext()
                .getResourceAsStream(path);
        Scanner sc= new Scanner(s);
        while(sc.hasNextLine()){
            listaNegra.add(sc.nextLine());
        }        
    }
    
    public UsuarioLogueado verificarUsuarioDominio(String nombreUsuario, String clave, String grupo) 
            throws PartialResultException, NamingException, UsuarioNoEncontradoException, UsuarioNoPerteneceAGrupoExeption, UsuarioEnListaNegraException{
        conexionLDAP.crearConexion(nombreUsuario, clave);
        this.usuario = conexionLDAP.getUsuario(nombreUsuario);
        conexionLDAP.cerrarConexion();
        
        verificarGrupoDominio(grupo);
        verificarListaNegraDominio(nombreUsuario);
        
        return new UsuarioLogueado(usuario);
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
}
