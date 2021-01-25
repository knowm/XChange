package org.knowm.xchange.bitmax;


import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api/pro/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface IBitmaxAuthenticated extends IBitmax{



}
