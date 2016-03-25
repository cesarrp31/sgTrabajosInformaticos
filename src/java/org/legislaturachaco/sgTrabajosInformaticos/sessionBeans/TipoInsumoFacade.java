/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.TipoInsumo;

/**
 *
 * @author Cesar
 */
@Stateless
public class TipoInsumoFacade extends AbstractFacade<TipoInsumo> {

    @PersistenceContext(unitName = "sgTrabajosInformaticosUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoInsumoFacade() {
        super(TipoInsumo.class);
    }
    
}
