package org.knowm.xchange.livecoin.service;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.LivecoinDigest;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.utils.DateUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LivecoinAccountServiceRaw extends LivecoinBaseService<Livecoin> {
  public LivecoinAccountServiceRaw(LivecoinExchange exchange) {
    super(Livecoin.class, exchange);
  }

  public Collection<Wallet> balances(String currency) throws IOException {
    List<Map> response = service.balances(apiKey, signatureCreator, currency);

    return LivecoinAdapters.adaptWallets(response);
  }

  public String withdraw(LivecoinWithdrawParams params) throws IOException {
    LivecoinResponse<Map> response = params.withdraw(service, apiKey, signatureCreator);

    if (!response.success)
      throw new ExchangeException("Failed to withdraw " + response.errorMessage);

    return response.data.get("id").toString();
  }

  public String walletAddress(Currency currency) throws IOException {
    Map response = service.paymentAddress(apiKey, signatureCreator, currency.getCurrencyCode());

    if (!response.containsKey("wallet"))
      throw new ExchangeException("Failed to get wallet address " + response.toString());

    return response.get("wallet").toString();
  }

  public List<FundingRecord> funding(Date start, Date end, Integer limit, Long offset) throws IOException {
    List<Map> response = service.transactions(apiKey, signatureCreator,
        String.valueOf(DateUtils.toMillisNullSafe(start)),
        String.valueOf(DateUtils.toMillisNullSafe(end)),
        "DEPOSIT,WITHDRAWAL",
        limit,
        offset
    );

//        if(!response.success)
//            throw new ExchangeException("Failed to get funding " + response.errorMessage);

    List<FundingRecord> resp = new ArrayList<>();
    for (Map map : response) {
      resp.add(LivecoinAdapters.adaptFundingRecord(map));
    }

    return resp;
  }

  interface LivecoinWithdrawParams {
    LivecoinResponse<Map> withdraw(Livecoin service, String apiKey, LivecoinDigest signatureCreator) throws IOException;
  }

}
