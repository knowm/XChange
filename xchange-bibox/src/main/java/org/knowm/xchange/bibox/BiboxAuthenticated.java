package org.knowm.xchange.bibox;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface BiboxAuthenticated extends Bibox {
}
