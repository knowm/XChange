package com.xeiam.xchange.vaultoro.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.vaultoro.VaultoroException;
import com.xeiam.xchange.vaultoro.dto.account.VaultoroBalance;
import com.xeiam.xchange.vaultoro.dto.account.VaultoroBalancesResponse;

public class VaultoroAccountServiceRaw extends VaultoroBasePollingService {

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
      VaultoroBalancesResponse response = vaultoro.getBalances(exchange.getNonceFactory(), apiKey, signatureCreator);
      return response.getData();
    } catch (VaultoroException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

}