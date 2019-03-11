package org.knowm.xchange.vaultoro.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.vaultoro.VaultoroException;
import org.knowm.xchange.vaultoro.dto.account.VaultoroBalance;
import org.knowm.xchange.vaultoro.dto.account.VaultoroBalancesResponse;

public class VaultoroAccountServiceRaw extends VaultoroBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public VaultoroAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public List<VaultoroBalance> getVaultoroBalances() throws VaultoroException, IOException {

    try {
      VaultoroBalancesResponse response =
          vaultoro.getBalances(exchange.getNonceFactory(), apiKey, signatureCreator);
      return response.getData();
    } catch (VaultoroException e) {
      throw new ExchangeException(e);
    }
  }
}
