package org.knowm.xchange.livecoin;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.livecoin.dto.marketdata.LIVECOINRestrictions;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface LIVECOIN {

	@GET
	@Path("exchange/restrictions")
	LIVECOINRestrictions getProducts() throws IOException;

}