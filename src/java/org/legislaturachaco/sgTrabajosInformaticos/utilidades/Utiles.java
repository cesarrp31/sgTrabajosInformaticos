/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;

/**
 *
 * @author Cesar
 */
public final class Utiles {
    
    public static final String ESTADO_NO_COMENZADO= "NO_COMENZADO",
                                ESTADO_ASIGNADO= "ASIGNADO",
                                ESTADO_EN_ESPERA= "EN_ESPERA",
                                ESTADO_CERRADO_SOLUCIONADO= "CERRADO_SOLUCIONADO",
                                ESTADO_CERRADO_NO_SOLUCIONADO= "CERRADO_NO_SOLUCIONADO";
    
    private Utiles(){
    }
    
    public static Object obtenerController(String nombreController){
        FacesContext fc= FacesContext.getCurrentInstance();
        return fc.getApplication().getELResolver().
                    getValue(fc.getELContext(), null, nombreController);
    }
    
    public static String fechaFormateada(Date fecha, String patron){
        SimpleDateFormat dt1 = new SimpleDateFormat(patron);
        return dt1.format(fecha);
    }
}
