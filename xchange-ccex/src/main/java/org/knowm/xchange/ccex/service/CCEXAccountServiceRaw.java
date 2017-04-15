package org.knowm.xchange.ccex.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ccex.dto.account.CCEXBalance;
import org.knowm.xchange.ccex.dto.account.CCEXBalanceResponse;
import org.knowm.xchange.ccex.dto.account.CCEXBalancesResponse;
import org.knowm.xchange.exceptions.ExchangeException;

public class CCEXAccountServiceRaw extends CCEXBaseService {

  public CCEXAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<CCEXBalance> getCCEXAccountInfo() throws IOException {

    CCEXBalancesResponse response = cCEXAuthenticated.balances(apiKey, signatureCreator, exchange.getNonceFactory());

    if (response.isSuccess()) {
      return response.getResult();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }

  public String getCCEXDepositAddress(String currency) throws IOException {

    CCEXBalanceResponse response = cCEXAuthenticated.getdepositaddress(apiKey, signatureCreator, exchange.getNonceFactory(), currency);

    if (response.isSuccess()) {
      return response.getResult().getCryptoAddress();
    } else {
      throw new ExchangeException(response.getMessage());
    }
  }
}
