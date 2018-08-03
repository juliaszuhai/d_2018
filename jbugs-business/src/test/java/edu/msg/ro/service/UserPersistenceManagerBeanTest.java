package edu.msg.ro.service;

import edu.msg.ro.boundary.UserManagement;
import edu.msg.ro.persistence.user.dao.UserPersistenceManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ejb.EJB;
import javax.inject.Inject;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceManagerBeanTest {



    @InjectMocks
    UserManagementBean userManagementBean;

    @Mock
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

        when(userPersistenceManager.findUsersNameStartingWith(any(String.class))).thenReturn(new ArrayList<>());
        String suffix = userManagementBean.createSuffix("dorel0");
        assertEquals(suffix, "");

    }

    @Test
    public void createSuffix_expected4(){


        when(userPersistenceManager.findUsersNameStartingWith(any(String.class)))
                .thenReturn(
                        new ArrayList<String>(){{
                            add("dorel0");
                            add("dorel01");
                            add("dorel02");
                            add("dorel03");
                        }}
                );
        String suffix = userManagementBean.createSuffix("dorel0");
        assertEquals(suffix, "4");

    }

    @Test
    public void createSuffix_expected7(){


        when(userPersistenceManager.findUsersNameStartingWith(any(String.class)))
                .thenReturn(
                        new ArrayList<String>(){{
                            add("dorel0");
                            add("dorel06");
                        }}
                );
        String suffix = userManagementBean.createSuffix("dorel0");
        assertEquals(suffix, "7");

    }



}