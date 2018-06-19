package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.account.LivecoinBalance;
import org.knowm.xchange.livecoin.dto.account.LivecoinPaymentOutResponse;
import org.knowm.xchange.livecoin.dto.account.LivecoinWalletAddressResponse;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;
import org.knowm.xchange.utils.DateUtils;

public class LivecoinAccountServiceRaw extends LivecoinBaseService<Livecoin> {
  public LivecoinAccountServiceRaw(LivecoinExchange exchange) {
    super(Livecoin.class, exchange);
  }

  public Wallet balances(String currency) throws IOException {
    List<LivecoinBalance> response = service.balances(apiKey, signatureCreator, currency);

    return LivecoinAdapters.adaptWallet(response);
  }

  public String withdraw(DefaultWithdrawFundsParams params) throws IOException {
    LivecoinPaymentOutResponse response =
        service.paymentOutCoin(
            apiKey,
            signatureCreator,
            params.getCurrency().getCurrencyCode(),
            params.getAmount(),
            params.getAddress());
    return response.getData().get("id").toString();
  }

  public String walletAddress(Currency currency) throws IOException {
    LivecoinWalletAddressResponse response =
        service.paymentAddress(apiKey, signatureCreator, currency.getCurrencyCode());
    return response.getWallet();
  }

  public List<Map> funding(Date start, Date end, Integer limit, Long offset) throws IOException {
    return service.transactions(
        apiKey,
        signatureCreator,
        String.valueOf(DateUtils.toMillisNullSafe(start)),
        String.valueOf(DateUtils.toMillisNullSafe(end)),
        "DEPOSIT,WITHDRAWAL",
        limit,
        offset);
  }
}
