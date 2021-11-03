package org.knowm.xchange.ftx;

import org.knowm.xchange.ftx.dto.FtxResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface FtxOtc {
    
    @GET
    @Path("/time")
    @Consumes(MediaType.APPLICATION_JSON)
    FtxResponse<String> time() throws IOException, FtxException;
}
