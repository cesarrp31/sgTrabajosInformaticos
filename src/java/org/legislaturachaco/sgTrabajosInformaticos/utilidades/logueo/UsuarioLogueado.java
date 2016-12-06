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

    public UsuarioLogueado(UsuarioDominio usuarioDominio, Usuarios usuario) {
        this.usuarioDominio = usuarioDominio;
        this.usuario = usuario;
    }

    public UsuarioDominio getUsuarioDominio() {
        return usuarioDominio;
    }

    public Usuarios getUsuario() {
        return usuario;
    }
    
    public String getNombreUsuario(){
        if(usuario == null) return usuarioDominio.getCommonName();
        else return usuario.getUsuario();
    }
}
