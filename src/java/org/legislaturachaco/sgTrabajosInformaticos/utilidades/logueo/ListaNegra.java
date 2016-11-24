/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar
 */
public class ListaNegra {
    
    /**
     * Devuelve la lista de aquellos usarios de dominio pertenecientes al grupo (que estan
     * permitido logearse), pero por algún motivo no se les permite loguearse al sistema
     * @return Lista negra usuarios del sistema
     */
    public static List<String> getListaNegraMiembrosGrupo(){
        return new ArrayList<>();
    }
    
    /**
     * Devuelve la lista de aquellos usarios que no estan en el dominio y que estan deshabilitados
     * @return Lista negra usuarios del sistema
     */
    public List<String> getListaNegraUsuarioLocales(){
        return new ArrayList<>();
    }
    
    
}
