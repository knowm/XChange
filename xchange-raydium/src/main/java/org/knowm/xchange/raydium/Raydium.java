package org.knowm.xchange.raydium;

import org.knowm.xchange.raydium.dto.FarmListDto;
import org.knowm.xchange.raydium.dto.LpListDto;
import org.knowm.xchange.raydium.dto.TokenListDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Raydium {

  @GET
  @Path("token/raydium.mainnet.json")
  @Consumes(MediaType.APPLICATION_JSON)
  TokenListDto getTokenList() throws IOException;

  @GET
  @Path("liquidity/mainnet.json")
  @Consumes(MediaType.APPLICATION_JSON)
  LpListDto getLpList() throws IOException;

  @GET
  @Path("farm/mainnet.json")
  @Consumes(MediaType.APPLICATION_JSON)
  FarmListDto getFarmList() throws IOException;
}
