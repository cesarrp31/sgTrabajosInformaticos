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
public class UsuarioNoEncontradoException extends Exception{
    
    public UsuarioNoEncontradoException(String lugarBusqueda){
        super("Usuario no encontrado en "+lugarBusqueda);
    }
    
    public UsuarioNoEncontradoException(){
        super("Usuario no encontrado");
    }
    
}
