package org.knowm.xchange.gemini.v1.service;

import static org.knowm.xchange.gemini.v1.GeminiUtils.convertToGeminiCcyName;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gemini.v1.dto.GeminiException;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiDepositAddressResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiTrailingVolumeRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiTrailingVolumeResponse;
import org.knowm.xchange.gemini.v1.dto.account.GeminiTransfer;
import org.knowm.xchange.gemini.v1.dto.account.GeminiTransfersRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalRequest;
import org.knowm.xchange.gemini.v1.dto.account.GeminiWithdrawalResponse;
import org.knowm.xchange.instrument.Instrument;
import si.mazi.rescu.SynchronizedValueFactory;

public class GeminiAccountServiceRaw extends GeminiBaseService {

  protected final List<Instrument> allCurrencyPairs;

  /**
   * Constructor
   *
   * @param exchange
   */
  public GeminiAccountServiceRaw(Exchange exchange) {
    super(exchange);
    this.allCurrencyPairs =
        new ArrayList<>(exchange.getExchangeMetaData().getInstruments().keySet());
  }

  public List<GeminiTransfer> transfers(Date from, Integer limit) throws IOException {
    SynchronizedValueFactory<Long> nonceFactory = exchange.getNonceFactory();
    GeminiTransfersRequest geminiTransfersRequest =
        GeminiTransfersRequest.create(from, limit, nonceFactory);
    return gemini.transfers(apiKey, payloadCreator, signatureCreator, geminiTransfersRequest);
  }

  public GeminiBalancesResponse[] getGeminiAccountInfo() throws IOException {
    try {
      GeminiBalancesRequest request =
          new GeminiBalancesRequest(String.valueOf(exchange.getNonceFactory().createValue()));
      return gemini.balances(apiKey, payloadCreator, signatureCreator, request);
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }

  public String withdraw(Currency currency, BigDecimal amount, String address) throws IOException {

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

  public GeminiDepositAddressResponse requestDepositAddressRaw(Currency currency)
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

  public GeminiTrailingVolumeResponse Get30DayTrailingVolumeDescription() throws IOException {
    try {
      GeminiTrailingVolumeRequest request =
          new GeminiTrailingVolumeRequest(String.valueOf(exchange.getNonceFactory().createValue()));

      return gemini.TrailingVolume(apiKey, payloadCreator, signatureCreator, request);
    } catch (GeminiException e) {
      throw handleException(e);
    }
  }
}
