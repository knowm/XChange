package org.knowm.xchange.bitmarket.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmarket.dto.account.BitMarketAccountInfoResponse;
import org.knowm.xchange.bitmarket.dto.account.BitMarketDepositResponse;
import org.knowm.xchange.bitmarket.dto.account.BitMarketWithdrawResponse;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author kfonal
 */
public class BitMarketAccountServiceRaw extends BitMarketBaseService {

  public BitMarketAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitMarketAccountInfoResponse getBitMarketAccountInfo() throws IOException, ExchangeException {

    BitMarketAccountInfoResponse response = bitMarketAuthenticated.info(apiKey, sign, exchange.getNonceFactory());

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketWithdrawResponse withdrawFromBitMarket(String currency, BigDecimal amount, String address) throws IOException, ExchangeException {

    BitMarketWithdrawResponse response = bitMarketAuthenticated.withdraw(apiKey, sign, exchange.getNonceFactory(), currency, amount, address);

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }

  public BitMarketDepositResponse depositToBitMarket(String currency) throws IOException, ExchangeException {

    BitMarketDepositResponse response = bitMarketAuthenticated.deposit(apiKey, sign, exchange.getNonceFactory(), currency);

    if (!response.getSuccess()) {
      throw new ExchangeException(String.format("%d: %s", response.getError(), response.getErrorMsg()));
    }

    return response;
  }
}
