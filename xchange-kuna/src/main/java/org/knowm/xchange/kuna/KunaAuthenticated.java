package org.knowm.xchange.kuna;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/** @author Dat Bui */
@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface KunaAuthenticated extends Kuna {}
