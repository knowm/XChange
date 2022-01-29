package org.knowm.xchange.coinone.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.dto.CoinoneException;
import org.knowm.xchange.coinone.dto.account.CoinoneBalancesRequest;
import org.knowm.xchange.coinone.dto.account.CoinoneBalancesResponse;
import org.knowm.xchange.coinone.dto.account.CoinoneWithdrawRequest;
import org.knowm.xchange.coinone.dto.account.CoinoneWithdrawResponse;
import org.knowm.xchange.currency.Currency;

public class CoinoneAccountServiceRaw extends CoinoneBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinoneAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CoinoneBalancesResponse getWallet() throws CoinoneException, IOException {
    CoinoneBalancesRequest request =
        new CoinoneBalancesRequest(apiKey, exchange.getNonceFactory().createValue());
    CoinoneBalancesResponse response = coinone.getWallet(payloadCreator, signatureCreator, request);
    return response;
  }

  public CoinoneWithdrawResponse withdrawFund(
      Currency currency, BigDecimal amount, String address, String authNumber)
      throws CoinoneException, IOException {
    CoinoneWithdrawRequest request =
        new CoinoneWithdrawRequest(
            apiKey,
            exchange.getNonceFactory().createValue(),
            currency.getSymbol().toLowerCase(),
            authNumber,
            amount.doubleValue(),
            address);
    CoinoneWithdrawResponse response =
        coinone.withdrawFund(payloadCreator, signatureCreator, request);
    return response;
  }
}
