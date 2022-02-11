/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inser.restful.restful_crud;

import innui.modelo.jpa.LinkedList_envuelta;
import static innui.modelo.jpa.PersistenceConfig.jdbc_contraseña;
import static innui.modelo.jpa.PersistenceConfig.jdbc_usuario;
import innui.modelo.jpa.peliculas.Film;
import static innui.modelo.jpa.peliculas.Film.SELECT_findAll_order;
import static innui.modelo.jpa.peliculas.Film.SELECT_findByFilm_id_order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.LinkedList;

/**
 *
 * @author informatica
 */
@jakarta.ejb.Stateless
@Path("inser.restful.restful_crud.film")
public class FilmFacadeREST extends AbstractFacade<Film> {
    @Context
    // opción 1: private UriInfo context;
    // opción 2: 
    private HttpServletRequest context;
    
//    @PersistenceContext(unitName = "restful_crudPU")
    public EntityManager entityManager;
    public Map<String, String> propiedades_mapa = new HashMap();

    public FilmFacadeREST() {
        super(Film.class);
    }
    /**
     * POST: Los campos de texto no se deben enviar en el Path debido a los caracteres especiales que pueden contener
     * @param entity 
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Film entity) {
        String texto;     
        // Usar cookies: context.getCookies();
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Film entity) {
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        Film film = super.find(id);
        super.remove(film);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Film find(@PathParam("id") String id) {
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        Film film = super.find(id);
        return film;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LinkedList_envuelta<Film> findAll_envuelta() {
        LinkedList_envuelta<Film> linkedList_envuelta = new LinkedList_envuelta();
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        List<Film> films_lista = new LinkedList<Film>(super.findAll());
        linkedList_envuelta.lista = new LinkedList(films_lista);
        return linkedList_envuelta;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LinkedList_envuelta<Film> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        LinkedList_envuelta<Film> linkedList_envuelta = new LinkedList_envuelta();
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        List<Film> films_lista = super.findRange(new int[]{from, to});
        linkedList_envuelta.lista = new LinkedList(films_lista);
        return linkedList_envuelta;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        String usuario;
        String contraseña;
        String [] error = { "" };
        usuario = propiedades_mapa.get(jdbc_usuario);
        contraseña = propiedades_mapa.get(jdbc_contraseña);
        entityManager = ApplicationConfig.getEntityManager("restful_crudPU"
                , usuario, contraseña, propiedades_mapa, error);
        return entityManager;
    }
    /**
     * POST: Los campos de texto no se deben enviar en el Path debido a los caracteres especiales que pueden contener
     * @param from
     * @param to
     * @param descripcion
     * @return 
     */
    @POST
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LinkedList_envuelta<Film> findLike_descripcion(@PathParam("from") Integer from, @PathParam("to") Integer to, String descripcion) {
        LinkedList_envuelta<Film> linkedList_envuelta = new LinkedList_envuelta();
        List<Film> films_lista;
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        TypedQuery<Film> typedQuery = getEntityManager().createNamedQuery("Film.findByCodigoFilm", Film.class);
        typedQuery = typedQuery.setParameter("descripcion", descripcion);
        typedQuery = typedQuery.setMaxResults(to - from + 1);
        typedQuery = typedQuery.setFirstResult(from);
        films_lista = typedQuery.getResultList();
        linkedList_envuelta.lista = new LinkedList(films_lista);
        return linkedList_envuelta;
    }
    
    @GET
    @Path("{from}/{to}/{campo_ordenacion}/{asc}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LinkedList_envuelta<Film> find_orden(@PathParam("from") Integer from
            , @PathParam("to") Integer to
            , @PathParam("campo_ordenacion") String campo_ordenacion
            , @PathParam("asc") String asc) {
        List<Film> films_lista;
        LinkedList_envuelta<Film> linkedList_envuelta = new LinkedList_envuelta();
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        String consulta = SELECT_findAll_order
                + campo_ordenacion;
        if (asc.toLowerCase().equals("desc")) {
            consulta = consulta + " DESC";
        }
        TypedQuery<Film> typedQuery = getEntityManager().createQuery(consulta, Film.class);
        typedQuery = typedQuery.setMaxResults(to - from + 1);
        typedQuery = typedQuery.setFirstResult(from);
        films_lista = typedQuery.getResultList();
        linkedList_envuelta.lista = new LinkedList(films_lista);
        return linkedList_envuelta;
    }
    /**
     * POST: Los campos de texto no se deben enviar en el Path debido a los caracteres especiales que pueden contener
     * @param from
     * @param to
     * @param descripcion
     * @param campo_ordenacion
     * @param asc
     * @return 
     */
    @POST
    @Path("{from}/{to}/{campo_ordenacion}/{asc}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LinkedList_envuelta<Film> findLike_descripcion_orden(@PathParam("from") Integer from
            , @PathParam("to") Integer to
            , String descripcion
            , @PathParam("campo_ordenacion") String campo_ordenacion
            , @PathParam("asc") String asc) {
        LinkedList_envuelta<Film> linkedList_envuelta = new LinkedList_envuelta();
        List<Film> films_lista;
        String texto;     
        texto = context.getHeader(jdbc_usuario);
        propiedades_mapa.put(jdbc_usuario, texto);
        texto = context.getHeader(jdbc_contraseña);        
        propiedades_mapa.put(jdbc_contraseña, texto);
        String consulta = SELECT_findByFilm_id_order
                + campo_ordenacion;
        if (asc.toLowerCase().equals("desc")) {
            consulta = consulta + " DESC";
        }
        TypedQuery<Film> typedQuery = getEntityManager().createQuery(consulta, Film.class);
        typedQuery = typedQuery.setParameter("description", descripcion);
        typedQuery = typedQuery.setMaxResults(to - from + 1);
        typedQuery = typedQuery.setFirstResult(from);
        films_lista = typedQuery.getResultList();
        linkedList_envuelta.lista = new LinkedList(films_lista);
        return linkedList_envuelta;
    }
    
}
