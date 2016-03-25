/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.TipoPatrimonio;

/**
 *
 * @author Cesar
 */
@Stateless
public class TipoPatrimonioFacade extends AbstractFacade<TipoPatrimonio> {

    @PersistenceContext(unitName = "sgTrabajosInformaticosUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoPatrimonioFacade() {
        super(TipoPatrimonio.class);
    }
    
}
