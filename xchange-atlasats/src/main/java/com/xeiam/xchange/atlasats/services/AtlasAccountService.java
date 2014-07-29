package com.xeiam.xchange.atlasats.services;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.atlasats.dtos.AtlasAccountInfo;

@Path("/api/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AtlasAccountService {

	@GET
	@Path("account")
	public AtlasAccountInfo getAccountInfo(
			@HeaderParam("Authorization") String apiKey);

	@GET
	@Path("market/symbols")
	public List<Map<String, Object>> getMarketSymbols(
			@HeaderParam("Authorization") String apiKey);

}
