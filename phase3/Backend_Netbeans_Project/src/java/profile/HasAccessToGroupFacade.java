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
public class HasAccessToGroupFacade extends AbstractFacade<HasAccessToGroup> {

    @PersistenceContext(unitName = "ProfileMinusPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HasAccessToGroupFacade() {
        super(HasAccessToGroup.class);
    }
    
}
