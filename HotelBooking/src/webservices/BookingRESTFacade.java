package webservices;

	import javax.persistence.EntityManager;
	import services.Booking;
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
	@Path("webservices.booking")
	@com.sun.jersey.spi.resource.Singleton

	public class BookingRESTFacade {
	    @PersistenceContext(unitName = "HotelBookingPU")
	    
	    protected EntityManager entityManager;

	    public BookingRESTFacade() {
	    }

	    @POST
	    @Consumes({"application/xml", "application/json"})
	    @Transactional
	    public Response create(Booking entity) {
	        entityManager.persist(entity);
	        return Response.created(URI.create(entity.getBookingId().toString())).build();
	    }

	    @PUT
	    @Consumes({"application/xml", "application/json"})
	    @Transactional
	    public void edit(Booking entity) {
	        entityManager.merge(entity);
	    }

	    @DELETE
	    @Path("{id}")
	    @Transactional
	    public void remove(@PathParam("id") Integer id) {
	        Booking entity = entityManager.getReference(Booking.class, id);
	        entityManager.remove(entity);
	    }

	    @GET
	    @Path("{id}")
	    @Produces({"application/xml", "application/json"})
	    @Transactional
	    public Booking find(@PathParam("id") Integer id) {
	        return entityManager.find(Booking.class, id);
	    }

	    @GET
	    @Produces({"application/xml", "application/json"})
	    @Transactional
	    public List<Booking> findAll() {
	        return find(true, -1, -1);
	    }

	    @GET
	    @Path("{max}/{first}")
	    @Produces({"application/xml", "application/json"})
	    @Transactional
	    public List<Booking> findRange(@PathParam("max") Integer max, @PathParam("first") Integer first) {
	        return find(false, max, first);
	    }

	    @GET
	    @Path("count")
	    @Produces("text/plain")
	    @Transactional
	    public String count() {
	        try {
	            Query query = entityManager.createQuery("SELECT count(o) FROM Booking AS o");
	            return query.getSingleResult().toString();
	        } finally {
	            entityManager.close();
	        }
	    }

	    private List<Booking> find(boolean all, int maxResults, int firstResult) {
	        try {
	            Query query = entityManager.createQuery("SELECT object(o) FROM Booking AS o");
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


