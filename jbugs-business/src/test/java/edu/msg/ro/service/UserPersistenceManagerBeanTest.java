package edu.msg.ro.service;

import edu.msg.ro.boundary.UserManagement;
import edu.msg.ro.persistence.user.dao.UserPersistenceManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ejb.EJB;
import javax.inject.Inject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceManagerBeanTest {



    @InjectMocks
    UserManagementBean userManagementBean;

    @InjectMocks
    UserPersistenceManager userPersistenceManager;

    @Test
    public void generateUsername_expectedMarini() {
        String username = userManagementBean.generateUsername("Ion", "Marin");
        assertEquals("marini", username);
    }

    @Test
    public void generateUsername_expectedIonion() {
        String username = userManagementBean.generateUsername("Ion", "Ion");
        assertEquals("ionion", username);
    }
    @Test
    public void generateUsername_expectedPetric() {
        String username = userManagementBean.generateUsername("Calin", "Petrindean");
        assertEquals("petric", username);
    }

    @Test
    public void generateUsername_expectedba0000() {
        String username = userManagementBean.generateUsername("a", "b");
        assertEquals("ba0000", username);
    }

    @Test
    public void createSuffix_expectedEmpty(){

        when(userPersistenceManager.findUsersNameStartingWith("dorel").thenReturn())
    }



}