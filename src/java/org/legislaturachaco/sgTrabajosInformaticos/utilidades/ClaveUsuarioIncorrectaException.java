/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

/**
 *
 * @author cesar
 */
public class ClaveUsuarioIncorrectaException extends Exception{

    public ClaveUsuarioIncorrectaException() {
        super("¡Clave de Usuario incorrecta!");
    }
    
}
