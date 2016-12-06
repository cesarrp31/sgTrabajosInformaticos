/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

/**
 *
 * @author coperalta
 */
public class UsuarioNoPerteneceAGrupoExeption extends Exception{

    public UsuarioNoPerteneceAGrupoExeption(String grupo, String usuario) {
        super("Usuario "+usuario+" no pertenece al grupo "+grupo);
    }
    
}
