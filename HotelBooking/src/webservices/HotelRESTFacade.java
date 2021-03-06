package webservices;

import javax.persistence.EntityManager;
import services.Hotel;
import java.net.URI;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ANMAGALH
 */
@Path("services.hotel")
@com.sun.jersey.spi.resource.Singleton
//@com.sun.jersey.api.spring.Autowire
public class HotelRESTFacade {
    @PersistenceContext(unitName = "HotelBookingPU")
    //@Error("Please fix your project manually, for instructions see http://wiki.netbeans.org/SpringWithAopalliance")
    protected EntityManager entityManager;

    public HotelRESTFacade() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void reserveRoom(Hotel hotel){
        hotel.setRoomsAvailable(hotel.getRoomsAvailable()-1);
        entityManager.merge(hotel.getRoomsAvailable());
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    @Transactional
    public Response create(Hotel entity) {
        entityManager.persist(entity);
        return Response.created(URI.create(entity.getHotelId().toString())).build();
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    @Transactional
    public void edit(Hotel entity) {
        entityManager.merge(entity);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void remove(@PathParam("id") Integer id) {
        Hotel entity = entityManager.getReference(Hotel.class, id);
        entityManager.remove(entity);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    @Transactional
    public Hotel find(@PathParam("id") Integer id) {
        return entityManager.find(Hotel.class, id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    @Transactional
    public List<Hotel> findAll() {
        return find(true, -1, -1);
    }

    @GET
    @Path("{max}/{first}")
    @Produces({"application/xml", "application/json"})
    @Transactional
    public List<Hotel> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
        return find(false, max, first);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    @Transactional
    public String count() {
        try {
            Query query = entityManager.createQuery("SELECT count(o) FROM Hotel AS o");
            return query.getSingleResult().toString();
        } finally {
            entityManager.close();
        }
    }

    private List<Hotel> find(boolean all, int maxResults, int firstResult) {
        try {
            Query query = entityManager.createQuery("SELECT object(o) FROM Hotel AS o");
            if (!all) {
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
            }
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    
}
