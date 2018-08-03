package edu.msg.ro.service;

import edu.msg.ro.boundary.UserManagement;
import org.junit.Test;

import javax.ejb.EJB;
import javax.inject.Inject;

import static org.junit.Assert.*;

public class UserPersistenceManagerBeanTest {


    UserManagementBean bean = new UserManagementBean();

    @Test
    public void generateUsername_expectedMarini() {
        String username = bean.generateUsername("Ion", "Marin");
        assertEquals("marini", username);
    }

    @Test
    public void generateUsername_expectedIonion() {
        String username = bean.generateUsername("Ion", "Ion");
        assertEquals("ionion", username);
    }
    @Test
    public void generateUsername_expectedPetric() {
        String username = bean.generateUsername("Calin", "Petrindean");
        assertEquals("petric", username);
    }

    @Test
    public void generateUsername_expectedba0000() {
        String username = bean.generateUsername("a", "b");
        assertEquals("ba0000", username);
    }

    @Test
    public void createSuffix_expectedEmpty(){

    }



}