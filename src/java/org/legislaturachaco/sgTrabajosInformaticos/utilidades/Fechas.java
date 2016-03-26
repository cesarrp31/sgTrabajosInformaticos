/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Cesar
 */
public abstract class Fechas {
    public static Date obtenerFechaActual(){
        return Calendar.getInstance().getTime();
    }
}
