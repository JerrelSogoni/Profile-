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
public class PersonalPageFacade extends AbstractFacade<PersonalPage> {

    @PersistenceContext(unitName = "ProfileMinusPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonalPageFacade() {
        super(PersonalPage.class);
    }
    
}
