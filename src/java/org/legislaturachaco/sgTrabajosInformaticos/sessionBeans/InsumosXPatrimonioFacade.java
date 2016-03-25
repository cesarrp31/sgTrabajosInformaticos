/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.legislaturachaco.sgTrabajosInformaticos.sessionBeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.legislaturachaco.sgTrabajosInformaticos.entidades.InsumosXPatrimonio;

/**
 *
 * @author Cesar
 */
@Stateless
public class InsumosXPatrimonioFacade extends AbstractFacade<InsumosXPatrimonio> {

    @PersistenceContext(unitName = "sgTrabajosInformaticosUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InsumosXPatrimonioFacade() {
        super(InsumosXPatrimonio.class);
    }
    
}
