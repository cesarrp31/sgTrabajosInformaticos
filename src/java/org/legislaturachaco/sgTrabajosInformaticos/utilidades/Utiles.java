/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import javax.faces.context.FacesContext;

/**
 *
 * @author Cesar
 */
public final class Utiles {
    
    private Utiles(){
    }
    
    public static Object obtenerController(String nombreController){
        FacesContext fc= FacesContext.getCurrentInstance();
        return fc.getApplication().getELResolver().
                    getValue(fc.getELContext(), null, nombreController);
    }
}
