package gob.bancomext;

import gob.bancomext.interfaces.SimpleSessionLocal;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * gob.bancomext.GenericResource
 * @author Alfredo Estrada
 */
@Path("generic")
@RequestScoped
public class GenericResource {

    @EJB
    SimpleSessionLocal simpleSessionLocalBean;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of gob.bancomext.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDate() {        
        return simpleSessionLocalBean.getDate();
    }

}
