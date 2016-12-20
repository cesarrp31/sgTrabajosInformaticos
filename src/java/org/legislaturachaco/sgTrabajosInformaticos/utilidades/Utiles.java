/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
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
    
    public static String sha256(String valor) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(valor.getBytes());

        byte byteData[] = md.digest();

        StringBuilder hexString = new StringBuilder();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	return hexString.toString();
    }
    
    public static void mensajesFacesContext(Severity severidad, String msg, String o){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(severidad, msg, o));
    }
}
