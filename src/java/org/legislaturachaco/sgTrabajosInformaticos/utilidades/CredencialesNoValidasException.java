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
public class CredencialesNoValidasException extends Exception{

    public CredencialesNoValidasException() {
        super("Nombre de usuario o contraseña incorrectos");
    }    
}
