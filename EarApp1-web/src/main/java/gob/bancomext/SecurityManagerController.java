package gob.bancomext;

import gob.bancomext.security.SecurityManager;
import gob.bancomext.security.SecurityToken;
import gob.bancomext.security.impl.SecurityManagerBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
 * gob.bancomext.SecurityManagerController
 * @author Alfredo Estrada
 */
@Path("/smc")
@RequestScoped
public class SecurityManagerController {

    @EJB
    SecurityManagerBean securityManagerBean;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public SecurityManagerController() {
    }

    @Path("/tokens")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getTokens() {
        final LinkedHashMap<String, SecurityToken> tokens = SecurityManager.getInstance().getTokens();
        System.out.println("==>>SecurityManagerController.getTokens:tokens="+tokens);
        List<String> tokensList = new ArrayList<>();
        tokensList.addAll(tokens.keySet());
        return tokensList.toString();
    }
    
    @Path("/date")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDate() {
        return securityManagerBean.getDate();
    }

}
