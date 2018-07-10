package org.knowm.xchange.coindirect;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface CoindirectAuthenticated extends Coindirect {
    public static final String AUTHORIZATION = "Authorization";
}
