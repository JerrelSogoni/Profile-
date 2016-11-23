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
public class UserPlusFacade extends AbstractFacade<UserPlus> {

    @PersistenceContext(unitName = "ProfileMinusPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserPlusFacade() {
        super(UserPlus.class);
    }
    
}