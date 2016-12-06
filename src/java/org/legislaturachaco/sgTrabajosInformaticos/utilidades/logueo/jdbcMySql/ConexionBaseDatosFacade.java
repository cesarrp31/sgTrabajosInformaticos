/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.utilidades.logueo.jdbcMySql;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.legislaturachaco.sgTrabajosInformaticos.sessionBeans.AbstractFacade;

/**
 *
 * @author cesar
 */
@Stateless
public class ConexionBaseDatosFacade extends AbstractFacade<ConexionBaseDatos>{
    
    @PersistenceContext(unitName = "sgTrabajosInformaticosUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConexionBaseDatosFacade() {
        super(ConexionBaseDatos.class);
    }
}
