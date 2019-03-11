package org.knowm.xchange.zaif;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.zaif.dto.ZaifException;
import org.knowm.xchange.zaif.dto.marketdata.ZaifFullBook;
import org.knowm.xchange.zaif.dto.marketdata.ZaifMarket;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Zaif {

  @GET
  @Path("api/1/depth/{baseCurrency}_{targetCurrency}")
  ZaifFullBook getDepth(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("targetCurrency") String targetCurrency)
      throws ZaifException, IOException;

  @GET
  @Path("api/1/currency_pairs/all")
  List<ZaifMarket> getCurrencyPairs();
}
