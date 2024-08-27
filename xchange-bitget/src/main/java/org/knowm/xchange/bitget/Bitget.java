package org.knowm.xchange.bitget;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.dto.BitgetCoinDto;
import org.knowm.xchange.bitget.dto.BitgetResponse;
import org.knowm.xchange.bitget.dto.BitgetServerTime;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitget {

  @GET
  @Path("api/v2/public/time")
  BitgetResponse<BitgetServerTime> serverTime() throws IOException;


  @GET
  @Path("api/v2/spot/public/coins")
  BitgetResponse<List<BitgetCoinDto>> coinDtos() throws IOException;


}
