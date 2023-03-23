package org.knowm.xchange.kuna;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/** @author Dat Bui */
@Path("/v2")
@Produces(MediaType.APPLICATION_JSON)
public interface KunaAuthenticated extends Kuna {}
