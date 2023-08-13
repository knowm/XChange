package org.knowm.xchange.bitcoincore;

import java.io.IOException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bitcoincore.dto.BitcoinCoreException;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceRequest;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceResponse;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreUnconfirmedBalanceRequest;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface BitcoinCore {

  @POST
  @Path("")
  BitcoinCoreBalanceResponse getBalance(BitcoinCoreBalanceRequest request)
      throws IOException, BitcoinCoreException;

  @POST
  @Path("")
  BitcoinCoreBalanceResponse getUnconfirmedBalance(BitcoinCoreUnconfirmedBalanceRequest request)
      throws IOException, BitcoinCoreException;
}