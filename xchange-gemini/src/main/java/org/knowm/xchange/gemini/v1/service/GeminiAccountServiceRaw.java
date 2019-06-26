package org.knowm.xchange.gemini.v1.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.account.*;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.knowm.xchange.gemini.v1.GeminiUtils.convertToGeminiCcyName;

public class GeminiAccountServiceRaw extends GeminiBaseService {

  protected final List<CurrencyPair> allCurrencyPairs;
  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.allCurrencyPairs =
        new ArrayList<CurrencyPair>(exchange.getExchangeMetaData().getCurrencyPairs().keySet());
  }

  public synchronized List<GeminiTransfer> transfers(Date from, Integer limit) throws IOException {
    SynchronizedValueFactory<Long> nonceFactory = exchange.getNonceFactory();
    GeminiTransfersRequest geminiTransfersRequest =
        GeminiTransfersRequest.create(from, limit, nonceFactory);
    return gemini.transfers(apiKey, payloadCreator, signatureCreator, geminiTransfersRequest);
  }

  public synchronized GeminiBalancesResponse[] getGeminiAccountInfo() throws IOException {
    try {
      GeminiBalancesRequest request =
          new GeminiBalancesRequest(String.valueOf(exchange.getNonceFactory().createValue()));
      GeminiBalancesResponse[] balances =
          gemini.balances(apiKey, payloadCreator, signatureCreator, request);
      return balances;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public synchronized String withdraw(Currency currency, BigDecimal amount, String address)
      throws IOException {

    try {
      String ccy = convertToGeminiCcyName(currency.getCurrencyCode());
      GeminiWithdrawalRequest request =
          new GeminiWithdrawalRequest(
              String.valueOf(exchange.getNonceFactory().createValue()), ccy, amount, address);

      GeminiWithdrawalResponse withdrawRepsonse =
          gemini.withdraw(apiKey, payloadCreator, signatureCreator, ccy, request);

      return withdrawRepsonse.txHash;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public synchronized GeminiDepositAddressResponse requestDepositAddressRaw(Currency currency)
      throws IOException {
    try {
      String ccy = convertToGeminiCcyName(currency.getCurrencyCode());

      GeminiDepositAddressRequest exchange =
          new GeminiDepositAddressRequest(
              String.valueOf(this.exchange.getNonceFactory().createValue()), ccy, null);

      GeminiDepositAddressResponse requestDepositAddressResponse =
          gemini.requestNewAddress(apiKey, payloadCreator, signatureCreator, ccy, exchange);
      if (requestDepositAddressResponse != null) {
        return requestDepositAddressResponse;
      } else {
        return null;
      }
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public synchronized GeminiTrailingVolumeResponse Get30DayTrailingVolumeDescription()
      throws IOException {
    try {
      GeminiTrailingVolumeRequest request =
          new GeminiTrailingVolumeRequest(String.valueOf(exchange.getNonceFactory().createValue()));

      GeminiTrailingVolumeResponse trailingVolResp =
          gemini.TrailingVolume(apiKey, payloadCreator, signatureCreator, request);
      return trailingVolResp;
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }
}
