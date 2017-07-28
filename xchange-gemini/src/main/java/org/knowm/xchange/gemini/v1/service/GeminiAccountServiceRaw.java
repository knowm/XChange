package org.knowm.xchange.gemini.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalResponse;

import java.io.IOException;
import java.math.BigDecimal;

import static org.knowm.xchange.gemini.v1.GeminiUtils.convertToGeminiCcyName;

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
      GeminiBalancesRequest request = new GeminiBalancesRequest(String.valueOf(exchange.getNonceFactory().createValue()));
      GeminiBalancesResponse[] balances = Gemini.balances(
          apiKey,
          payloadCreator,
          signatureCreator,
          request);
      return balances;
    } catch (GeminiException e) {
      throw new ExchangeException(e);
    }
  }

  public String withdraw(Currency currency, BigDecimal amount, String address) throws IOException {

    String ccy = convertToGeminiCcyName(currency.getCurrencyCode());
    GeminiWithdrawalRequest request = new GeminiWithdrawalRequest(
        String.valueOf(exchange.getNonceFactory().createValue()),
        ccy,
        amount,
        address);

    GeminiWithdrawalResponse withdrawRepsonse = Gemini.withdraw(
        apiKey,
        payloadCreator,
        signatureCreator,
        ccy,
        request);

    return withdrawRepsonse.txHash;
  }

  public GeminiDepositAddressResponse requestDepositAddressRaw(Currency currency) throws IOException {
    try {
      String ccy = convertToGeminiCcyName(currency.getCurrencyCode());

      GeminiDepositAddressRequest exchange = new GeminiDepositAddressRequest(
          String.valueOf(this.exchange.getNonceFactory().createValue()), ccy, null);

      GeminiDepositAddressResponse requestDepositAddressResponse = Gemini.requestNewAddress(
          apiKey,
          payloadCreator,
          signatureCreator,
          ccy,
          exchange);
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
