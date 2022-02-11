/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inser.restful.restful_crud;

import static innui.modelo.jpa.PersistenceConfig.jdbc_contraseña;
import static innui.modelo.jpa.PersistenceConfig.jdbc_usuario;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Application;

/**
 *
 * @author informatica
 */
@jakarta.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {
    public static Map<String, EntityManager> entityManager_map = new HashMap();
    
    public static EntityManager getEntityManager(String unitName, String usuario, String contraseña, Map<String, String> propiedades_map, String [] error) {
        EntityManagerFactory entityManagerFactory;
        EntityManager entityManager;
        String clave = unitName
            + "-"
            + usuario
            + "-" 
            + contraseña;
        entityManager = entityManager_map.get(clave);
        if (entityManager == null || entityManager.isOpen() == false) {
            propiedades_map.put(jdbc_usuario, usuario);
            propiedades_map.put(jdbc_contraseña, contraseña);
            entityManagerFactory = Persistence.createEntityManagerFactory(unitName, propiedades_map);
//            entityManagerFactory = Persistence.createEntityManagerFactory(unitName);
            entityManager = entityManagerFactory.createEntityManager();
            if (entityManager != null) {
                entityManager_map.put(clave, entityManager);
            }
        }
        return entityManager;
    }
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(inser.restful.restful_crud.FilmFacadeREST.class);
    }
    
}
