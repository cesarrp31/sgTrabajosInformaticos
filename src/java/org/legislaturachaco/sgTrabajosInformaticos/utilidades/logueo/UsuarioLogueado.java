/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo;

import org.legislaturachaco.sgTrabajosInformaticos.entidades.Usuarios;
import org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.ldap.UsuarioDominio;

/**
 *
 * @author coperalta
 */
public class UsuarioLogueado {
    
    private final UsuarioDominio usuarioDominio;
    private final Usuarios usuario;

    private UsuarioLogueado(UsuarioDominio usuarioDominio, Usuarios usuario) {
        this.usuarioDominio = usuarioDominio;
        this.usuario = usuario;
    }
    
    public UsuarioLogueado(UsuarioDominio usuarioDominio){
        this(usuarioDominio, null);
    }
    
    public UsuarioLogueado(Usuarios usuarios){
        this(null, usuarios);
    }

    public String getNombreUsuario(){
        if(usuario == null) return usuarioDominio.getNombre()+" "+usuarioDominio.getApellido();
        else {
            String nomAp="";
            if(usuario.getIdTecnico()!=null) nomAp+=": "+
                    usuario.getIdTecnico().getApellido()+" "+usuario.getIdTecnico().getNombre();
            return usuario.getUsuario()+nomAp;
        }
    }
}
