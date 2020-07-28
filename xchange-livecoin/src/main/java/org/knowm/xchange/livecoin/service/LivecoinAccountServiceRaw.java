package org.knowm.xchange.livecoin.service;

import static org.knowm.xchange.client.ResilienceRegistries.NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.account.LivecoinBalance;
import org.knowm.xchange.livecoin.dto.account.LivecoinPaymentOutResponse;
import org.knowm.xchange.livecoin.dto.account.LivecoinWalletAddressResponse;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.utils.DateUtils;

public class LivecoinAccountServiceRaw extends LivecoinBaseService {
  public LivecoinAccountServiceRaw(
      LivecoinExchange exchange, Livecoin livecoin, ResilienceRegistries resilienceRegistries) {
    super(exchange, livecoin, resilienceRegistries);
  }

  public List<LivecoinBalance> balances(String currency) throws IOException {
    return decorateApiCall(() -> service.balances(apiKey, signatureCreator, currency))
        .withRetry(retry("balances"))
        .call();
  }

  public String withdraw(DefaultWithdrawFundsParams params) throws IOException {
    LivecoinPaymentOutResponse response =
        decorateApiCall(
                () ->
                    service.paymentOutCoin(
                        apiKey,
                        signatureCreator,
                        params.getCurrency().getCurrencyCode(),
                        params.getAmount(),
                        params.getAddress()))
            .withRetry(retry("paymentOutCoin", NON_IDEMPOTENTE_CALLS_RETRY_CONFIG_NAME))
            .call();
    return response.getData().get("id").toString();
  }

  public String walletAddress(Currency currency) throws IOException {
    LivecoinWalletAddressResponse response =
        decorateApiCall(
                () -> service.paymentAddress(apiKey, signatureCreator, currency.getCurrencyCode()))
            .withRetry(retry("paymentAddress"))
            .call();
    return response.getWallet();
  }

  public List<Map> funding(Date start, Date end, Integer limit, Long offset) throws IOException {
    return decorateApiCall(
            () ->
                service.transactions(
                    apiKey,
                    signatureCreator,
                    String.valueOf(DateUtils.toMillisNullSafe(start)),
                    String.valueOf(DateUtils.toMillisNullSafe(end)),
                    "DEPOSIT,WITHDRAWAL",
                    limit,
                    offset))
        .withRetry(retry("transactions"))
        .call();
  }
}
