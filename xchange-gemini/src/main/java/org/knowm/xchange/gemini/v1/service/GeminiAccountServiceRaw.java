package org.knowm.xchange.gemini.v1.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiMarginInfosRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiMarginInfosResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalResponse;

public class GeminiAccountServiceRaw extends GeminiBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiAccountServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public GeminiBalancesResponse[] getGeminiAccountInfo() throws IOException {

    try {
      GeminiBalancesResponse[] balances = Gemini.balances(apiKey, payloadCreator, signatureCreator,
          new GeminiBalancesRequest(String.valueOf(exchange.getNonceFactory().createValue())));
      return balances;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public GeminiMarginInfosResponse[] getGeminiMarginInfos() throws IOException {

    try {
      GeminiMarginInfosResponse[] marginInfos = Gemini.marginInfos(apiKey, payloadCreator, signatureCreator,
          new GeminiMarginInfosRequest(String.valueOf(exchange.getNonceFactory().createValue())));
      return marginInfos;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public String withdraw(String withdrawType, String walletSelected, BigDecimal amount, String address) throws IOException {

    GeminiWithdrawalResponse[] withdrawRepsonse = Gemini.withdraw(apiKey, payloadCreator, signatureCreator,
        new GeminiWithdrawalRequest(String.valueOf(exchange.getNonceFactory().createValue()), withdrawType, walletSelected, amount, address));
    return withdrawRepsonse[0].getWithdrawalId();
  }

  public GeminiDepositAddressResponse requestDepositAddressRaw(String currency) throws IOException {
    try {
      String type = "unknown";
      if (currency.equalsIgnoreCase("BTC")) {
        type = "bitcoin";
      } else if (currency.equalsIgnoreCase("LTC")) {
        type = "litecoin";
      } else if (currency.equalsIgnoreCase("ETH")) {
        type = "ethereum";
      }

      GeminiDepositAddressResponse requestDepositAddressResponse = Gemini.requestDeposit(apiKey, payloadCreator, signatureCreator,
          new GeminiDepositAddressRequest(String.valueOf(exchange.getNonceFactory().createValue()), type, "exchange", 0));
      if (requestDepositAddressResponse != null) {
        return requestDepositAddressResponse;
      } else {
        return null;
      }
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

}
