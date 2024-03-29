/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profile;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author yunjoon_soh
 */
@Stateless
public class BuyFacade extends AbstractFacade<Buy> {

    @PersistenceContext(unitName = "ProfileMinusPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BuyFacade() {
        super(Buy.class);
    }
    
}
